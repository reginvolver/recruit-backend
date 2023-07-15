package com.yundingshuyuan.recruit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yundingshuyuan.recruit.dao")
public class RecruitApplication {
    public static void main(String[] args) {
        System.setProperty("apollo.config-service", "http://localhost:8080");
        SpringApplication.run(RecruitApplication.class, args);
    }
}