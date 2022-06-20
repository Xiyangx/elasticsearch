package com.sunac.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/2 9:48 上午
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class User {
    private String name;
    private String age;
}
