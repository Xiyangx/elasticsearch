package com.sunac.elasticsearch.service;

import com.sunac.elasticsearch.entity.Report;

import java.util.List;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/8 3:25 下午
 * @Version 1.0
 */
public interface EsService {
    /**
     * @Description: TODO
     * @Param: []
     * @Return: java.util.List<com.sunac.elasticsearch.entity.FakerUser>
     * @Author: xiyang
     * @Date 2022/6/8 3:27 下午
     **/
    List<Report> getReportList(String companyCode, String year, String month);
    /**
     * @Description: 单个账目数据的导出
     * @Param: [filePath, yaer, month, bsegGjahr, bsegH2Monat, bsegBukrs, bsegHkont, bsegZzwyfwlx, bsegKostl, csksKtext, bsegPrctr, cepcKtext, bsegZzlfinr, lfa1Name1, bsegZzkunnr, kna1Name1]
     * @Return: java.util.List<com.sunac.elasticsearch.entity.Report>
     * @Author: xiyang
     * @Date 2022/6/27 2:59 下午
     **/
    List<Report> getReportList(String companyCode, String year, String month,String bsegHkont,String bsegZzwyfwlx,String bsegKostl,String csksKtext,
                    String bsegPrctr,String cepcKtext,String bsegZzlfinr,String lfa1Name1,String bsegZzkunnr,
                    String kna1Name1);
    /**
     * @Description: 写入csv
     * @Param: [list]
     * @Return: void
     * @Author: xiyang
     * @Date 2022/6/10 12:14 下午
     **/
    void writeExcel(String filePath,String yaer, String month);
    /**
     * @Description: 单个账目的导出
     * @Param: [filePath, yaer, month, bsegGjahr, bsegH2Monat, bsegBukrs, bsegHkont, bsegZzwyfwlx, bsegKostl, csksKtext, bsegPrctr, cepcKtext, bsegZzlfinr, lfa1Name1, bsegZzkunnr, kna1Name1]
     * @Return: void
     * @Author: xiyang
     * @Date 2022/6/27 2:59 下午
     **/
    void writeExcel(String filePath,String bsegGjahr,String bsegH2Monat,
                    String bsegBukrs,String bsegHkont,String bsegZzwyfwlx,String bsegKostl,String csksKtext,
                    String bsegPrctr,String cepcKtext,String bsegZzlfinr,String lfa1Name1,String bsegZzkunnr,
                    String kna1Name1);
}
