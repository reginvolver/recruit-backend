package com.yundingshuyuan.recruit;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.yundingshuyuan.recruit.dao")
@EnableKnife4j
@Slf4j
@EnableScheduling
public class RecruitApplication {
    public static void main(String[] args) {
        System.setProperty("apollo.config-service", "http://localhost:8080");
        try {
            SpringApplication.run(RecruitApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
