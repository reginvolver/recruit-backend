package com.yundingshuyuan.recruit.web.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yundingshuyuan.recruit.api.InterviewResultService;
import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/27 15:51
 */
@Slf4j
@RecruitResult
@Tag(name = "面试结果相关")
@RestController
@RequestMapping("/interviewResult")
public class InterviewResultController {

    @Autowired
    InterviewResultService interviewResultService;

    @Operation(summary = "展示自己面试过的人的record")
    @SaCheckPermission("admin")
    @PostMapping("/ownResultSimple")
    public List<InterviewRecordVo> adminRecord(@RequestParam("group_id") Integer group_id){
        return interviewResultService.showAdminRecord(group_id);
    }

    @Operation(summary = "展示所有人面试record信息")
    @SaCheckPermission("super-admin")
    @GetMapping("/allResultSimple")
    public List<InterviewRecordVo> allResult(){
        return interviewResultService.showAllRecord();
    }


}
