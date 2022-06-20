package com.sunac.elasticsearch.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: 日期参数类
 * @Author xiyang
 * @Date 2022/6/9 7:11 下午
 * @Version 1.0
 */
public class ArgsUtils {

    /**
     * @Description: 获取当前每年首月到当前月的集合
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     * @Author: xiyang
     * @Date 2022/6/10 11:35 上午
     **/
    public List<String> getBeforeMonth() {

        int month = Integer.parseInt(getMonth());
        List<String> monthList = new ArrayList<>();
        //每年1月份的时候，需要去年整年的数据，所以返回整年的月份集合
        if (month == 1) {
            int cursor = 12 + 1;
            for (int i = 1; i < cursor; i++) {
                String strMonth = "0" + i;
                monthList.add(strMonth);
            }
        } else {
            for (int i = 1; i < month; i++) {
                String strMonth = "0" + i;
                monthList.add(strMonth);
            }
        }
        return monthList;
    }

    /**
     * @Description: 获取当前月份
     * @Param: []
     * @Return: java.lang.String
     * @Author: xiyang
     * @Date 2022/6/9 7:17 下午
     **/
    public String getMonth() {
        return new SimpleDateFormat("MM").format(new Date());
    }

    /**
     * @Description: 获取当前年份
     * @Param: []
     * @Return: java.lang.String
     * @Author: xiyang
     * @Date 2022/6/9 7:17 下午
     **/
    public String getyear() {
        //每年1月份的时候，需要去年整年的数据，所以返回去年的年份
        if (Integer.parseInt(getMonth()) == 1) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, -1);
            Date date = c.getTime();
            return new SimpleDateFormat("yyyy").format(date);
        }
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    /**
     * @Description: 获取 分区
     * @Param: []
     * @Return: java.lang.String
     * @Author: xiyang
     * @Date 2022/6/14 2:23 下午
     **/
    public String getPartition(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    }
}