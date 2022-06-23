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
     * @Description: 写入csv
     * @Param: [list]
     * @Return: void
     * @Author: xiyang
     * @Date 2022/6/10 12:14 下午
     **/
    void writeExcel(String filePath,String yaer, String month);
}
