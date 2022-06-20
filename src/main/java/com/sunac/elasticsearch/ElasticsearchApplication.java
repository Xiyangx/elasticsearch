package com.sunac.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @Description: TODO
 * @Author xiyang
 * @Date 2022/6/1 3:16 下午
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages="com.sunac.elasticsearch")
@MapperScan("com.sunac.elasticsearch.mapper")
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

}
