package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.WorkScheduleService;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.domain.vo.WorkingScheduleVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/28 17:23
 */

@Slf4j
@RecruitResult
@Tag(name = "面试官排班")
@RestController
@RequestMapping("/workAssign")
public class WorkScheduleController {

    @Autowired
    WorkScheduleService workScheduleService;

    @Operation(summary = "展示今日排班")
    @GetMapping("/showTodayAssign")
    public List<WorkingScheduleVo> showTodayAssign(){
        LocalDate today = LocalDate.now();
        return workScheduleService.showCertainDayAssign(today);
    }


    @Operation(summary = "第二天面试排班")
    @GetMapping("/workingScheduleAssignTomorrow")
    public String workAssign(){
        return workScheduleService.assignTomorrow();
    }

    @Operation(summary = "当日临时加面试组")
    @PostMapping("/tempAddInterview")
    public String tempAddInterview(@RequestBody InterviewPositionVo interviewPositionVo){
        Boolean aBoolean = workScheduleService.tempAddInterview(interviewPositionVo);
        if (aBoolean){
            return "临时面试点开放成功";
        }
        return "临时面试点开放失败";

    }

    @Operation(summary = "清空今日排班信息")
    @GetMapping("/deleteAssign")
    public String deleteAssign(){
        String s = workScheduleService.deleteTodaySchedule();
        return s;
    }
    @Operation(summary = "面试官换班，超管来换")
    @PostMapping("/changeAssign")
    public String changeAssign(@RequestBody List<InterviewPositionVo> interviewPositionVos){
        return workScheduleService.changeSchedule(interviewPositionVos);
    }

}
