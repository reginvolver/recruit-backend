package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yundingshuyuan.recruit.api.WorkScheduleService;
import com.yundingshuyuan.recruit.domain.WorkingSchedule;
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
    @SaCheckPermission("admin")
    @GetMapping("/showTodayAssign")
    public List<WorkingScheduleVo> showTodayAssign(){
        LocalDate today = LocalDate.now();
        return workScheduleService.showCertainDayAssign(today);
    }


    @Operation(summary = "第二天面试排班")
    @SaCheckPermission("admin")
    @GetMapping("/workingScheduleAssignTomorrow")
    public boolean workAssign(){
        return workScheduleService.assignTomorrow();
    }


    @Operation(summary = "当日临时加面试组")
    @SaCheckPermission("super-admin")
    @PostMapping("/tempAddInterview")
    public boolean tempAddInterview(@RequestBody InterviewPositionVo interviewPositionVo){
        boolean aBoolean = false;

            aBoolean = workScheduleService.tempAddInterview(interviewPositionVo);
        if (aBoolean){
            return true;
        }
        return false;
    }

    @Operation(summary = "清空今日排班信息")
    @SaCheckPermission("super-admin")
    @GetMapping("/deleteAssign")
    public boolean deleteAssign(){
        return workScheduleService.deleteTodaySchedule();
    }


    @Operation(summary = "面试官换班")
    @SaCheckPermission("super-admin")
    @PostMapping("/changeAssign")
    public boolean changeAssign(@RequestBody List<WorkingSchedule> workingSchedules){
        return workScheduleService.changeSchedule(workingSchedules);
    }

}
