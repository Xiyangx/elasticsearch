package com.sunac.elasticsearch.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.sunac.elasticsearch.entity.Company;
import com.sunac.elasticsearch.entity.Report;
import com.sunac.elasticsearch.service.EsService;
import com.sunac.elasticsearch.utils.ArgsUtils;
import com.sunac.elasticsearch.utils.EsUtil;
import com.sunac.elasticsearch.utils.ElasticSearchPoolUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            //从连接池里获取es连接
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
        //创建放数据的list
        List<Report> reports = null;
        try {
            //获取数据
            reports = EsUtil.searchResponse(client, must);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭es连接
        ElasticSearchPoolUtil.returnClient(client);
        //返回数据
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
    public void writeExcel(String filePath, String yaer, String monthStr ) {
        //获取大区列表
        List<String> companyAreaList = hiveSqlServiceImpl.getCompanyAreaList();
        //获取各个大区下面公司代码和公司名称的集合
        Map<String, List<Company>> areaMap = hiveSqlServiceImpl.getAreaMap(companyAreaList);
        //遍历集合，每个每个公司每个月份的数据写到excel
        Set<Map.Entry<String, List<Company>>> entries = areaMap.entrySet();
        for (Map.Entry<String, List<Company>> entry : entries) {
            //获取大区名字
            String area = entry.getKey();
            //获取该大区下的公司代码和公司名称的列表
            List<Company> companies = entry.getValue();
            //遍历
            for (Company company : companies) {
                //获取年初1月当前月份的月份列表 [01,02,03]
                List<String> beforeMonth = ArgsUtils.getBeforeMonth(monthStr);
                //声明输出流
                FileOutputStream outputStream = null;
                try {
                    //创建文件
                    outputStream = new FileOutputStream(filePath + area + "/" + company.getHcode()+company.getHname() + ".xlsx");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //创建excel对象
                ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
                //每个月一个sheet页
                for (String month : beforeMonth) {
                    //从es获取每个月份的数据
                    List<Report> reportList = getReportList(company.getHcode(), yaer, month);
                    //创建写入sheet页的对象
                    WriteSheet sheet = EasyExcel.writerSheet(Integer.parseInt(month), month).head(Report.class).build();
                    //写入sheet
                    excelWriter.write(reportList, sheet);
                    logger.info("----------- {} {}月 写入完成 -----------", company.getHname(), month);
                }
                //关闭流
                excelWriter.finish();
                try {
                    //关闭输出流
                    assert outputStream != null;
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
