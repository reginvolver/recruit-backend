package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.RegisterInfoService;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RecruitResult
@Tag(name = "报名接口")
@Slf4j
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterInfoService registerInfoService;


    @PostMapping("/upload")
    @Operation(summary = "提交申请书")
    public BasicResultVO upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) throws FileNotFoundException {
        log.info("{}", id);
        String s = registerInfoService.submitApplicationPhoto(file, id);
        if (s == null) {
            return BasicResultVO.fail("上传失败");
        }
        return BasicResultVO.success("上传成功", s);
    }
}
