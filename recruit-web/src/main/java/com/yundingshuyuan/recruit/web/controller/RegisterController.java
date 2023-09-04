package com.yundingshuyuan.recruit.web.controller;


import com.yundingshuyuan.recruit.api.RegisterInfoService;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RecruitResult
@Tag(name = "报名接口")
@Slf4j
@RequestMapping("/miniapp/register")
public class RegisterController {
    @Autowired
    private RegisterInfoService registerInfoService;


    @PostMapping("/upload")
    @Operation(summary = "提交申请书")
    public BasicResultVO upload(@RequestParam("file") MultipartFile file, @RequestParam("cloudId") String cloudId) throws FileNotFoundException {
        String s = registerInfoService.submitApplicationPhoto(file, cloudId);
        if (s == null) {
            return BasicResultVO.fail("上传失败");
        }
        return BasicResultVO.success("上传成功", s);
    }
}