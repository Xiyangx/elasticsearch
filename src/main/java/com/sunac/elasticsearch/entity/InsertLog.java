package com.sunac.elasticsearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/13 4:52 下午
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@TableName("ads_fd_sap_hs_journal_excel_log")
public class InsertLog {
    private String logyear;
    private String logmonth;
}
