package com.sunac.elasticsearch.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.sunac.elasticsearch.entity.Company;
import com.sunac.elasticsearch.entity.Report;
import com.sunac.elasticsearch.service.EsService;
import com.sunac.elasticsearch.utils.ArgsUtils;
import com.sunac.elasticsearch.utils.ESUtil;
import com.sunac.elasticsearch.utils.ElasticSearchPoolUtil;
import org.apache.lucene.search.TotalHits;
import org.apache.tools.ant.taskdefs.Sleep;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/8 3:27 下午
 * @Version 1.0
 */
@Service
public class EsServiceImpl implements EsService {


    @Autowired
    private HiveSqlServiceImpl hiveSqlServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);

    private final ArgsUtils argsUtils = new ArgsUtils();

    RestHighLevelClient client = null;


    /**
     * @Description: 获取每个公司的每年每月的数据
     * @Param: [companyCode, year, month]
     * @Return: java.util.List<com.sunac.elasticsearch.entity.Report>
     * @Author: xiyang
     * @Date 2022/6/10 12:15 下午
     **/
    @Override
    public List<Report> getReportList(String companyCode, String year, String month) {

        try {
            client = ElasticSearchPoolUtil.getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建复合查询条件对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //条件
        TermsQueryBuilder termsQueryBuilder1 = QueryBuilders.termsQuery("bsegbukrs.keyword", companyCode);
        TermsQueryBuilder termsQueryBuilder2 = QueryBuilders.termsQuery("bseggjahr.keyword", year);
        TermsQueryBuilder termsQueryBuilder3 = QueryBuilders.termsQuery("bsegh2monat.keyword", month);
        BoolQueryBuilder must = boolQueryBuilder.must(termsQueryBuilder1).must(termsQueryBuilder2).must(termsQueryBuilder3);


        List<Report> reports = null;
        try {
            reports = ESUtil.searchResponse(client, must);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ElasticSearchPoolUtil.returnClient(client);

        return reports;
    }

    /**
     * @Description: 把数据写入csv文件
     * @Param: [list]
     * @Return: void
     * @Author: xiyang
     * @Date 2022/6/10 12:16 下午
     **/
    @Override
    public void writeExcel(String filePath) {
        List<Company> companyList = hiveSqlServiceImpl.getCompanyList();

        for (Company company : companyList) {


            List<String> beforeMonth = argsUtils.getBeforeMonth();
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(filePath + "/" + company.getHname() + ".xlsx");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            Sleep sleep = new Sleep();
            for (String month : beforeMonth) {
                List<Report> reportList = getReportList(company.getHcode(), argsUtils.getyear(), month);

                sleep.execute();

                WriteSheet sheet = EasyExcel.writerSheet(Integer.parseInt(month), month).head(Report.class).build();

                //然后写到第一个sheet，名字为模板 然后文件流会自动关闭
                excelWriter.write(reportList, sheet);
                logger.info("----------- {} {}月 写入完成 -----------", company.getHname(), month);
            }
            //关闭流
            excelWriter.finish();
            //
            try {
                assert outputStream != null;
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
