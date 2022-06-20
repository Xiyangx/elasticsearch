package com.sunac.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunac.elasticsearch.entity.InsertLog;
import com.sunac.elasticsearch.mapper.InsertLogMapper;
import com.sunac.elasticsearch.service.InsertLogService;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/13 4:49 下午
 * @Version 1.0
 */
@Service
@DS("slave_mysql")
public class InserLogServiceImpl extends ServiceImpl<InsertLogMapper, InsertLog> implements InsertLogService {

}
