package com.sunac.elasticsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunac.elasticsearch.entity.InsertLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/13 4:51 下午
 * @Version 1.0
 */
@Repository
@Mapper
public interface InsertLogMapper extends BaseMapper<InsertLog> {
}
