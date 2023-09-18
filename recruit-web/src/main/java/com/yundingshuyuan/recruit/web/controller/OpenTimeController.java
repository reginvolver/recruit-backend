package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yundingshuyuan.recruit.api.OpenTimeService;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import com.yundingshuyuan.recruit.web.annotation.PageQuery;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RecruitResult
@RequiredArgsConstructor
@SaCheckLogin
@SaCheckRole("super-admin")
@SaCheckPermission("super-admin:opentime")
@Tag(name = "管理开放预约面试时间接口")
@RequestMapping("/superAdmin/openTime")
public class OpenTimeController {
    private final OpenTimeService openTimeService;


    @PutMapping("/batch")
    @Operation(summary = "一次设置多段预约面试时间")
    public boolean batchSetOpenTime(@RequestBody OpenTimeInfoVo... infos) {
        return openTimeService.batchSetOpenTime(infos);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "删除多个预约面试时间")
    public int batchDeleteOpenTime(@RequestParam long... ids) {
        return openTimeService.batchDeleteOpenTime(ids);
    }

    @PostMapping("/batch")
    @Operation(summary = "修改一个指定的预定面试时间段信息")
    public Object batchChangeOpenTime(@RequestBody OpenTimeInfoVo... info) {
        return openTimeService.batchChangeOpenTime(info);
    }

    @GetMapping("/{date}")
    @Operation(summary = "根据日期获取当天预定面试时间段")
    public List<OpenTimeInfoVo> getOpenTimeInfoByTime(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return openTimeService.getOpenTimeInfoByDate(localDate);
    }

    @GetMapping("/all")
    @PageQuery
    @Operation(summary = "获取所有预定面试时间段")
    public List<OpenTimeInfoVo> getAllOpenTimeInfo() {
        return openTimeService.getAllOpenTimeInfo();
    }

    @GetMapping("/page/{current}")
    @Operation(summary = "获取预定面试时间段/分页")
    public IPage<OpenTimeInfoVo> getPageOpenTimeInfo(
            @PathVariable("current") long current, @RequestParam long size) {
        return openTimeService.getPageOpenTimeInfo(current, size);
    }

}
