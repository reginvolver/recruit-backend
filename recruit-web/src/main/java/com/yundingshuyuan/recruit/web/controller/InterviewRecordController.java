package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.recruit.api.InterviewRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RecruitResult
@RestController
@SaCheckRole("user")
@Tag(name = "面试结果查询接口")
@RequestMapping("/interview")
public class InterviewRecordController {

    private final InterviewRecordService interviewRecordService;

    public InterviewRecordController(InterviewRecordService interviewRecordService) {
        this.interviewRecordService = interviewRecordService;
    }


//    /**
//     * 查询面试结果
//     * @param cloudId 用户微信的cloud_id
//     * @return
//     */
//    @GetMapping("/{cloudId}/result")
//    public ResponseEntity<String> getInterviewResultByCloudId(@PathVariable("cloudId") String cloudId) {
//
//
//        Boolean isPassed = interviewRecordService.isInterviewPassed(cloudId);
//        if (isPassed == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (isPassed) {
//            return ResponseEntity.ok("面试通过");
//        } else {
//            return ResponseEntity.ok("面试未通过");
//        }
//    }
}