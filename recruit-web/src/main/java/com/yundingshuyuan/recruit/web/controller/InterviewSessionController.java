package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.IInterviewPositionService;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 面试场次表 前端控制器
 * </p>
 *
 * @author NSC
 * @since 2023-07-31
 */
@RestController
@Tag(name = "面试地点接口")
@RecruitResult
@Slf4j
@RequestMapping("/interview")
public class InterviewSessionController {

    @Autowired
    IInterviewPositionService interviewPositionService;

    @PostMapping("/add")
    @Operation(summary = "添加一个面试地点")
    BasicResultVO addInterviewPosition(@RequestBody InterviewPositionVo interviewPositionVo) {
        return interviewPositionService.addInterviewPosition(interviewPositionVo)
                ? BasicResultVO.success("添加成功")
                : BasicResultVO.fail("添加失败");
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取全部面试地点")
    List<InterviewPositionVo> getAllInterviewPosition() {
        return interviewPositionService.selectAll();
    }

    @GetMapping("/deleteById")
    @Operation(summary = "根据id删除一个面试地点")
    BasicResultVO deleteInterviewPositionById(int id) {
        return interviewPositionService.deleteById(id)
                ? BasicResultVO.success("删除成功")
                : BasicResultVO.fail("删除失败");
    }

    @GetMapping("assignAll")
    @Operation(summary = "为所有时段的报名新生分配面试地点")
    BasicResultVO assignAllInterviewPosition(int id) {
        return interviewPositionService.assignAllInterviewPosition()
                ? BasicResultVO.success("分配成功")
                : BasicResultVO.fail("分配失败");
    }

    @GetMapping("assign")
    @Operation(summary = "为某一时段的报名新生分配面试地点")
    BasicResultVO assignInterviewPosition(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime interviewTime) {
        return interviewPositionService.assignInterviewPosition(interviewTime)
                ? BasicResultVO.success("分配成功")
                : BasicResultVO.fail("分配失败");
    }
}
