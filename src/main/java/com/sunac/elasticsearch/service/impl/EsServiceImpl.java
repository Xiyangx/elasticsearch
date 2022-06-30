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
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public List<Report> getReportList(String companyCode, String year, String month, String bsegHkont, String bsegZzwyfwlx,
                                      String bsegKostl, String csksKtext, String bsegPrctr, String cepcKtext,
                                      String bsegZzlfinr, String lfa1Name1, String bsegZzkunnr, String kna1Name1) {
        try {
            //从连接池里获取es连接
            client = ElasticSearchPoolUtil.getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("{}/{}/{}/{}/{}/{}/{}/{}/{}/{}/{}/{}/{}/",companyCode,year,month,bsegHkont,bsegZzwyfwlx,bsegKostl,csksKtext,bsegPrctr,cepcKtext,bsegZzlfinr,lfa1Name1,bsegZzkunnr,kna1Name1);
        //创建复合查询条件对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder termsQueryBuilder1 = QueryBuilders.matchQuery("bsegbukrs.keyword", companyCode);
        MatchQueryBuilder termsQueryBuilder2 = QueryBuilders.matchQuery("bseggjahr.keyword", year);
        MatchQueryBuilder termsQueryBuilder3 = QueryBuilders.matchQuery("bsegh2monat.keyword", month);
        MatchQueryBuilder termsQueryBuilder4 = QueryBuilders.matchQuery("bseghkont.keyword", bsegHkont);
        BoolQueryBuilder must = boolQueryBuilder
                .must(termsQueryBuilder1)
                .must(termsQueryBuilder2)
                .must(termsQueryBuilder3)
                .must(termsQueryBuilder4);
        String isEmpty = " ";
        //条件
        if (!bsegZzwyfwlx.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder5 = QueryBuilders.matchQuery("bsegzzwyfwlx", bsegZzwyfwlx.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder5);
        }
        if (!bsegKostl.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder6 = QueryBuilders.matchQuery("bsegkostl", bsegKostl.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder6);
        }
        if (!csksKtext.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder7 = QueryBuilders.matchQuery("csksktext", csksKtext.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder7);
        }
        if (!bsegPrctr.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder8 = QueryBuilders.matchQuery("bsegprctr", bsegPrctr.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder8);
        }
        if (!cepcKtext.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder9 = QueryBuilders.matchQuery("cepcktext", cepcKtext.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder9);
        }
        if (!bsegZzlfinr.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder10 = QueryBuilders.matchQuery("bsegzzlfinr", bsegZzlfinr.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder10);
        }
        if (!lfa1Name1.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder11 = QueryBuilders.matchQuery("lfa1name1", lfa1Name1.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder11);
        }
        if (!bsegZzkunnr.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder12 = QueryBuilders.matchQuery("bsegzzkunnr", bsegZzkunnr.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder12);
        }
        if (!kna1Name1.contains(isEmpty)){
            MatchQueryBuilder termsQueryBuilder13 = QueryBuilders.matchQuery("kna1name1", kna1Name1.replace(","," "));
            boolQueryBuilder.must(termsQueryBuilder13);
        }

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
    public void writeExcel(String filePath, String yaer, String monthStr, Map<String, List<Company>> areaMap ) {

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

    @Override
    public void writeExcel(List<Company> areaList, String filePath, String bsegGjahr, String bsegH2Monat, String bsegBukrs, String bsegHkont, String bsegZzwyfwlx, String bsegKostl, String csksKtext, String bsegPrctr, String cepcKtext, String bsegZzlfinr, String lfa1Name1, String bsegZzkunnr, String kna1Name1) {

        //遍历
        for (Company company : areaList) {
            //获取年初1月当前月份的月份列表 [01,02,03]
            List<String> beforeMonth = ArgsUtils.getBsegH2Monat(bsegH2Monat);
            //判断是否整个excel都是空的
            int excelIsEmpty = 0;
            //汇总每个月份的数据列表
            List<Report> reportList = new ArrayList<>();
            //每个月一个sheet页
            for (String s : beforeMonth) {
                //从es获取每个月份的数据
                List<Report> reports = getReportList(company.getHcode(), bsegGjahr, s, bsegHkont, bsegZzwyfwlx, bsegKostl, csksKtext, bsegPrctr, cepcKtext, bsegZzlfinr, lfa1Name1, bsegZzkunnr, kna1Name1);
                reportList.addAll(reports);
                excelIsEmpty = excelIsEmpty + reports.size();

            }
            //判断是否整个excel都是空的,空的时候删除

            if (excelIsEmpty != 0){
                //声明输出流
                FileOutputStream outputStream = null;
                try {
                    //创建文件
                    outputStream = new FileOutputStream(filePath + company.getHcode()+company.getHname() + ".xlsx");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //创建excel对象
                ExcelWriter excelWriter = EasyExcel.write(outputStream).build();


                //创建写入sheet页的对象
                WriteSheet sheet = EasyExcel.writerSheet(1).head(Report.class).build();
                //写入sheet
                excelWriter.write(reportList, sheet);
                logger.info("----------- {} 写入完成 -----------", company.getHname());
                //关闭流
                excelWriter.finish();
                try {
                    //关闭输出流
                    assert outputStream != null;
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                logger.info("----------- {} 文件没有数据，不写入excel -----------", company.getHname());
            }
        }

    }
}
