package com.sunac.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sunac.elasticsearch.entity.User;
import com.sunac.elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/2 10:44 上午
 * @Version 1.0
 */
@Service
@DS("master")
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Description: TODO
     * @Param: []
     * @Return: java.util.List
     * @Author: xiyang
     * @Date 2022/6/2 5:52 下午
     **/
    @Override
    public List<User> selectAll() {
        return jdbcTemplate.query("select * from sunac.`user`", (resultSet, i) -> new User(resultSet.getString("name"), resultSet.getString("age")));
    }
}
