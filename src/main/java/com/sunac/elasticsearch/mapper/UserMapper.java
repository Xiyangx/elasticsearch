package com.sunac.elasticsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunac.elasticsearch.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/2 9:49 上午
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
