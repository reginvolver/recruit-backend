package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.OpenTimeService;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@Tag(name = "管理开放预约面试时间接口")
@RecruitResult
@Slf4j
@RequestMapping("/opentime")
public class OpenTimeController {

    @Autowired
    private OpenTimeService openTimeService;

    @PutMapping("/one")
    @Operation(summary = "设置一段预约面试时间")
    public boolean setOneOpenTime(@RequestBody OpenTimeInfoVo info) {
        return openTimeService.setOneOpenTime(info);
    }

    @PutMapping("/multi")
    @Operation(summary = "一次设置多段预约面试时间")
    public boolean setMultipleTime(@RequestBody OpenTimeInfoVo... infos) {
        return openTimeService.setMultipleTime(infos);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除一个预约面试时间")
    public int deleteOneOpenTime(@PathVariable("id") long id) {
        return openTimeService.deleteOneOpenTime(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "删除一个预约面试时间")
    public int deleteMutipleTime(@RequestParam long... ids) {
        return openTimeService.deleteMutipleTime(ids);
    }

    @PostMapping("/one")
    @Operation(summary = "修改一个指定的预定面试时间段信息")
    public Object changeOneOpenTimeInfo(@RequestBody OpenTimeInfoVo info) {
        return openTimeService.changeOneOpenTime(info);
    }

    @GetMapping("/{date}")
    @Operation(summary = "根据日期获取当天预定面试时间段")
    public List<OpenTimeInfoVo> getOpenTimeInfoByTime(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return openTimeService.getOpenTimeInfoByDate(localDate);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有预定面试时间段")
    public List<OpenTimeInfoVo> getAllOpenTimeInfo() {
        return openTimeService.getAllOpenTimeInfo();
    }

    @GetMapping("/page/{current}")
    @Operation(summary = "获取预定面试时间段/分页")
    public Object getPageOpenTimeInfo(
            @PathVariable("current") long current, @RequestParam long size) {
        return openTimeService.getPageOpenTimeInfo(current, size);
    }

}
