package com.sunac.elasticsearch.service;

import com.sunac.elasticsearch.entity.Company;

import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/2 8:56 上午
 * @Version 1.0
 */
public interface HiveSqlService {
    /**
     * @Description: 获取公司大区列表
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     * @Author: xiyang
     * @Date 2022/6/22 4:36 下午
     **/
    List<String> getCompanyAreaList();
    /**
     * @Description: 获取大区下面公司的集合
     * @Param: [list]
     * @Return: java.util.Map<java.lang.String,java.util.List<com.sunac.elasticsearch.entity.Company>>
     * @Author: xiyang
     * @Date 2022/6/22 4:37 下午
     **/
    Map<String,List<Company>> getAreaMap(List<String> list);
}
