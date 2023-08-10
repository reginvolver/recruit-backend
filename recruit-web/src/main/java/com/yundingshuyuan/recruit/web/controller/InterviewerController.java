package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.InterviewerService;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RecruitResult
@Tag(name = "面试官接口")
@Slf4j
@RequestMapping("/interviewer")
public class InterviewerController {
    @Autowired
    private InterviewerService interviewerService;

    @PostMapping("/generate")
    @Operation(summary = "生成面试小组信息")
    public void excel() throws FileNotFoundException {
        interviewerService.excel();
    }
}
