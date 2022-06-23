package com.sunac.elasticsearch.utils;

import java.text.SimpleDateFormat;
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
     * @Param: [monthStr]
     * @Return: java.util.List<java.lang.String>
     * @Author: xiyang
     * @Date 2022/6/22 4:42 下午
     **/
    public static List<String> getBeforeMonth(String monthStr) {

        int month = Integer.parseInt(monthStr);
        List<String> monthList = new ArrayList<>();
        for (int i = 1; i <= month; i++) {
            String strMonth = "0" + i;
            if (i > 9){
                monthList.add(strMonth.substring(1));
            } else {
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
    public static String getMonth() {
        return new SimpleDateFormat("MM").format(new Date());
    }

    /**
     * @Description: 获取当前年份
     * @Param: []
     * @Return: java.lang.String
     * @Author: xiyang
     * @Date 2022/6/9 7:17 下午
     **/
    public static String getyear() {
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
    public static String getPartition(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    }
}
