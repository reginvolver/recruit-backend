package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yundingshuyuan.recruit.api.InterviewService;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;
import com.yundingshuyuan.recruit.domain.vo.IntervieweeVo;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/25 9:39
 */
@Slf4j
@RecruitResult
@Tag(name = "面试实时操作")
@RestController
@RequestMapping("/interview")
public class InterviewController {


    @Autowired
    private InterviewService interviewService;

    @Operation(summary = "所有时间段内所有面试场地")
    @SaCheckPermission("admin")
    @GetMapping("/showInterviewLocation")
    public List<InterviewPositionVo> showInterviewLocation(){
        return interviewService.showAllInterviewLocation();
    }

    @Operation(summary = "该时段该面试场地人员")
    @SaCheckPermission("admin")
    @PostMapping("/showInterviewee")
    public List<UserInfoVo> showInterviewee(@RequestBody IntervieweeVo intervieweeVo){
        return interviewService.currentInterviewee(intervieweeVo);
    }

    @Operation(summary = "是否存在面试记录信息")
    @SaCheckPermission("admin")
    @PostMapping("/recordIfExist")
    public InterviewRecordVo recordIfExist(@RequestParam("user_id") Integer userId){
        return interviewService.interviewRecordIsExist(userId);
    }

    @Operation(summary = "当前面试新生的申请书")
    @SaCheckPermission("admin")
    @PostMapping("/returnApplicationPhotos")
    public List<String> returnApplicationPhotos(@RequestParam("cloudId") String cloudId){
        return interviewService.returnApplicationPhotoUrls(cloudId);
    }

    @Operation(summary = "提交面评")
    @SaCheckPermission("admin")
    @PostMapping("/submitFaceback")
    public boolean submitFaceback(@RequestBody InterviewRecordVo interviewRecordVo){
        return interviewService.submitFaceback(interviewRecordVo);
    }


}
