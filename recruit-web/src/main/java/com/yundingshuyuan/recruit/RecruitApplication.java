package com.yundingshuyuan.recruit;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.mzt.logapi.starter.annotation.EnableLogRecord;
import org.dromara.dynamictp.core.spring.EnableDynamicTp;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDynamicTp
@MapperScan("com.yundingshuyuan.recruit.dao")
@EnableKnife4j
@EnableLogRecord(tenant = "com.yundingshuyuan.recruit")
public class RecruitApplication {
    public static void main(String[] args) {
        System.setProperty("apollo.config-service", "http://localhost:8080");
        SpringApplication.run(RecruitApplication.class, args);
    }
}