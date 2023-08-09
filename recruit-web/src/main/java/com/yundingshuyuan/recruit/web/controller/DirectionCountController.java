package com.yundingshuyuan.recruit.web.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.yundingshuyuan.recruit.api.DirectionCountService;
import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
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
@SaCheckRole("admin")
@Tag(name = "报名情况统计接口")
@RequestMapping("/direction")
public class DirectionCountController {

    @Autowired
    private DirectionCountService directionCountService;
    /**
     * 统计各方向报名人数，当日报名人数，男女比例
     * @return
     */
    @GetMapping("/count")
    public List<DirectionCountVo> getDirectionCounts() {
        return directionCountService.getDirectionCounts();
    }

    /**
     *统计总的每一天的报名人数
     * @return
     */
    @GetMapping("/total")
    public List<Map<LocalDate, Integer>> getTotalCounts() {
        return directionCountService.getTotalCounts();
    }
}

