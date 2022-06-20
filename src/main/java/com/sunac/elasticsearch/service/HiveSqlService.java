package com.sunac.elasticsearch.service;

import com.sunac.elasticsearch.entity.Company;
import com.sunac.elasticsearch.entity.Report;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/2 8:56 上午
 * @Version 1.0
 */
public interface HiveSqlService {
    /**
     * @return java.util.List<String>
     * @Description: 获取组织架构
     * @Param: []
     * @Return: java.util.List<com.sunac.elasticsearch.entity.User>
     * @Author: xiyang
     * @Date 2022/6/8 2:22 下午
     **/
    List<Company> getCompanyList();
}
