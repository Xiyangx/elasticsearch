package com.sunac.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sunac.elasticsearch.entity.Company;
import com.sunac.elasticsearch.entity.Report;
import com.sunac.elasticsearch.service.HiveSqlService;
import com.sunac.elasticsearch.utils.ArgsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/8 2:26 下午
 * @Version 1.0
 */
@Service
@DS("master")
public class HiveSqlServiceImpl implements HiveSqlService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Company> getCompanyList() {
        System.out.println(222222222);
        String sql = "SELECT hcode,hname FROM sunac.`dwd_fd_sap_hs_ztfi0005_df` " +
                "WHERE SUBSTR(NIDUP,0,3) ='E02' " +
                "AND ntype='F' " +
                "AND stat_date =" + new ArgsUtils().getPartition();
        return jdbcTemplate.query(sql, (resultSet, i) -> new Company(resultSet.getString("hcode"), resultSet.getString("hname")));
    }
}
