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
public class InterviewPositionController {

    @Autowired
    IInterviewPositionService interviewPositionService;

    @PostMapping("/add")
    @Operation(summary = "添加一个面试地点")
    BasicResultVO addInterviewPosition(@RequestBody InterviewPositionVo interviewPositionVo) {
        interviewPositionService.addInterviewPosition(interviewPositionVo);
        return BasicResultVO.success("添加成功");
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取全部面试地点")
    List<InterviewPositionVo> getAllInterviewPosition() {
        return interviewPositionService.selectAll();
    }

    @GetMapping("/deleteById")
    @Operation(summary = "根据id删除一个面试地点")
    BasicResultVO deleteInterviewPositionById(int id) {
        interviewPositionService.deleteById(id);
        return BasicResultVO.success("删除成功");
    }

    @GetMapping("assignAll")
    @Operation(summary = "为所有时段的报名新生分配面试地点")
    BasicResultVO assignAllInterviewPosition() {
        interviewPositionService.assignAllInterviewPosition();
        return BasicResultVO.success("分配成功");
    }

    @GetMapping("assign")
    @Operation(summary = "为某一时段的报名新生分配面试地点")
    BasicResultVO assignInterviewPosition(
            @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")LocalDateTime interviewTime) {
        interviewPositionService.assignInterviewPosition(interviewTime);
        return BasicResultVO.success("分配成功");
    }

    @GetMapping("updatePosition")
    @Operation(summary = "根据用户id和面试地点id修改面试地点")
    BasicResultVO updateInterviewPosition(
            Integer userId,Integer positionId) {
        interviewPositionService.updateInterviewPosition(userId,positionId);
        return BasicResultVO.success("修改面试地点成功");
    }
    @GetMapping("exchangePosition")
    @Operation(summary = "根据用户id交换面试地点")
    BasicResultVO exchangeInterviewPosition(
            Integer userId1,
            Integer userId2) {
        interviewPositionService.exchangeInterviewPosition(userId1,userId2);
        return BasicResultVO.success("交换面试地点成功");
    }

}
