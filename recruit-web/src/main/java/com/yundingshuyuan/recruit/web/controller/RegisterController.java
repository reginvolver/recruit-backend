package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.RegisterInfoService;
import com.yundingshuyuan.recruit.domain.vo.RegisterInfoVO;
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
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterInfoService registerInfoService;

    @PostMapping("/save")
    @Operation(summary = "保存报名信息")
    public BasicResultVO save(@RequestBody RegisterInfoVO registerInfoVO) {
        if (registerInfoService.saveRegisterInfo(registerInfoVO)) {
            return BasicResultVO.success("保存成功");
        }
        return BasicResultVO.fail("保存失败");
    }

    @PostMapping("/update")
    @Operation(summary = "修改报名信息")
    private BasicResultVO update(@RequestBody RegisterInfoVO registerInfoVO) {
        if (registerInfoService.updateRegisterInfo(registerInfoVO)) {
            return BasicResultVO.success("修改成功");
        }
        return BasicResultVO.fail("修改失败");
    }


    @PostMapping("/upload")
    @Operation(summary = "提交申请书")
    public BasicResultVO upload(@RequestParam("file") MultipartFile file, Integer id) throws FileNotFoundException {
        String s = registerInfoService.submitApplicationPhoto(file, id);
        if (s == null) {
            return BasicResultVO.fail("上传失败");
        }
        return BasicResultVO.success("上传成功", s);
    }
}
