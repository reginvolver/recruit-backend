package com.yundingshuyuan.recruit.web.controller;


import com.yundingshuyuan.recruit.api.DirectionCountService;
import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RecruitResult
@RestController
@Tag(name = "报名统计")
@RequestMapping("/direction")
public class DirectionCountController {

    @Autowired
    private DirectionCountService directionCountService;

    @Operation(summary = "方向统计")
    @GetMapping("/count")
    public List<DirectionCountVo> getDirectionCounts() {
        return directionCountService.getDirectionCounts();
    }
    @Operation(summary = "报名时间统计")
    @GetMapping("/date")
    public List<Map<LocalDate, Integer>> getDateCounts() {
        return directionCountService.getDateCounts();
    }
    @Operation(summary = "报名人数统计")
    @GetMapping("/total")
    public Map<String, Object> getTotalCounts() {
        return directionCountService.getTotalCounts();
    }
}