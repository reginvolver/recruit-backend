package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.yundingshuyuan.recruit.api.InterviewRecordService;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RecruitResult
@RestController
@Tag(name = "面试结果查询接口")
@RequestMapping("/miniapp/interview")
public class InterviewRecordController {

    @Autowired
    private InterviewRecordService interviewRecordService;

    /**
     * 查询面试结果
     * @return
     */
    @Operation(summary = "面试结果查询")
    @GetMapping("/{userId}/result")
    public Integer getInterviewResultByCloudId(@PathVariable("userId") Integer userId) {


        return interviewRecordService.isInterviewPassed(userId);

    }
}