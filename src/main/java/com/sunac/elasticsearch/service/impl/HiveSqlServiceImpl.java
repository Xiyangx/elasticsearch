package com.sunac.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sunac.elasticsearch.entity.Company;
import com.sunac.elasticsearch.service.HiveSqlService;
import com.sunac.elasticsearch.utils.ArgsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<String> getCompanyAreaList() {
        String sql = "SELECT hname FROM sunac.`dwd_fd_sap_hs_ztfi0005_df` " +
                "WHERE NIDUP ='E02' " +
                "AND stat_date = " + ArgsUtils.getPartition() +" " +
                "GROUP by hname";
        return jdbcTemplate.query(sql,(resultSet , i) -> resultSet.getString("hname"));
    }

    @Override
    public Map<String, List<Company>> getAreaMap(List<String> list) {

        Map<String, List<Company>> listHashMap = new HashMap<>();
        for (String s : list) {
            String sql = "SELECT hcode,hname FROM sunac.`dwd_fd_sap_hs_ztfi0005_df` " +
                    "WHERE SUBSTR(NIDUP,0,3) ='E02' " +
                    "AND ntype='F' " +
                    "AND stat_date =" + ArgsUtils.getPartition() + " " +
                    "and nupnm = '" + s + "'";
            List<Company> companyList = jdbcTemplate.query(sql, (resultSet, i) -> new Company(resultSet.getString("hcode"), resultSet.getString("hname")));
            listHashMap.put(s,companyList);
        }
        return listHashMap;
    }

    @Override
    public List<Company> getSimpleAreaMap(List<String> list) {
        StringBuilder hcodes = new StringBuilder();
        for (String s : list) {
            hcodes.append("'").append(s).append("'").append(",");
        }
        String substring = hcodes.substring(0, hcodes.length() - 1);

        String sql = "SELECT hcode,hname FROM sunac.`dwd_fd_sap_hs_ztfi0005_df` " +
                "WHERE SUBSTR(NIDUP,0,3) ='E02' " +
                "AND ntype='F' " +
                "AND stat_date =" + ArgsUtils.getPartition() + " " +
                "and hcode in ( " + substring + ")" + " " +
                "group by hcode,hname";

        return jdbcTemplate.query(sql, (resultSet, i) -> new Company(resultSet.getString("hcode"), resultSet.getString("hname")));
    }
}
