package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.domain.OpenTimeInfo;
import com.yundingshuyuan.recruit.service.opentime.OpenTimeServiceImpl;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "管理开放预约面试时间接口")
@RecruitResult
@Slf4j
@RequestMapping("/opentime")
public class OpenTimeController {

    @Autowired
    private OpenTimeServiceImpl openTimeService;

    @PostMapping("/setOne")
    @Operation(summary = "设置一段预约面试时间")
    public boolean setOneOpenTime(@RequestBody OpenTimeInfo info) {
        return openTimeService.setOpenTime(info);
    }

    @PostMapping("/setMulti")
    @Operation(summary = "一次设置多段预约面试时间")
    public boolean setMultipleTime(@RequestBody OpenTimeInfo... info) {
        for (OpenTimeInfo openTimeInfo : info) {
            setOneOpenTime(openTimeInfo);
        }
        return true;
    }

}
