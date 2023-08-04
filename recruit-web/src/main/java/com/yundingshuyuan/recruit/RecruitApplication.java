package com.yundingshuyuan.recruit;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yundingshuyuan.recruit.dao")
@EnableKnife4j
public class RecruitApplication {
    public static void main(String[] args) {
        System.setProperty("apollo.config-service", "http://localhost:8080");
        SpringApplication.run(RecruitApplication.class, args);
    }
}