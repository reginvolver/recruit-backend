package com.yundingshuyuan.recruit.web.controller;

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
    @GetMapping("/showInterviewLocation")
    public List<InterviewPositionVo> showInterviewLocation(){
        return interviewService.showAllInterviewLocation();
    }

    @Operation(summary = "该时段该面试场地人员")
    @PostMapping("/showInterviewee")
    public List<UserInfoVo> showInterviewee(@RequestBody IntervieweeVo intervieweeVo){
        return interviewService.currentInterviewee(intervieweeVo);
    }

    @Operation(summary = "是否存在面试记录信息")
    @PostMapping("/recordIfExist")
    public InterviewRecordVo recordIfExist(@RequestParam("user_id") Integer userId){
        return interviewService.interviewRecordIsExist(userId);
    }

    @Operation(summary = "当前面试新生的申请书")
    @PostMapping("/returnApplicationPhotos")
    public List<String> returnApplicationPhotos(@RequestParam("user_id") Integer userId){
        return interviewService.returnApplicationPhotoUrls(userId);
    }

    @Operation(summary = "提交面评")
    @PostMapping("/submitFaceback")
    public String submitFaceback(@RequestBody InterviewRecordVo interviewRecordVo){
        boolean b = interviewService.submitFaceback(interviewRecordVo);
        if (b){
            return "提交成功";
        }
        return "提交失败";
    }


}
