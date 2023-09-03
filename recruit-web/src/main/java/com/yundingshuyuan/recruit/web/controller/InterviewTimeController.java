package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.recruit.api.InterviewTimeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RecruitResult
@RestController
@Tag(name = "预约面试时间接口")
@RequestMapping("/miniapp/interviewTime")
public class InterviewTimeController {

    @Autowired
    private InterviewTimeService interviewTimeService;

    /**
     * 预约面试时间
     * @param cloudId 用户微信的cloud_id
     * @param startTime 选择的面试时间段的开始时间
     * @return
     */
    @PostMapping("/reserveInterview")
    public ResponseEntity<String> reserveInterview(@RequestParam("cloud_id") String cloudId,
                                                   @RequestParam("start_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startTime) {
        boolean success = interviewTimeService.reserveInterview(cloudId, startTime);
        if (success) {
            return ResponseEntity.ok("预约成功");
        } else {
            return ResponseEntity.badRequest().body("预约失败");
        }
    }
}