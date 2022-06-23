package com.sunac.elasticsearch.service;

import com.sunac.elasticsearch.entity.User;

import java.util.List;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/2 10:13 上午
 * @Version 1.0
 */
public interface UserService {
    /**
     * @Description: 查询全部
     * @Param: []
     * @Return: java.util.List
     * @Author: xiyang
     * @Date 2022/6/2 10:45 上午
     */
    List<User> selectAll();

}
