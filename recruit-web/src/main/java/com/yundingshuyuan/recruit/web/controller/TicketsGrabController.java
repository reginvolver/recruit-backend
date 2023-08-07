package com.yundingshuyuan.recruit.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.yundingshuyuan.recruit.api.TicketGrabService;
import com.yundingshuyuan.recruit.domain.vo.LectureTicketVo;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 抢票系统
 *
 * @Author cr
 * @Date 2023/8/1 1:46
 */

@Slf4j
@RecruitResult
@Tag(name = "面试官排班")
@RestController
@RequestMapping("/ticket")
public class TicketsGrabController {

    @Resource
    TicketGrabService ticketGrabService;


    @Operation(summary = "抢票")
    @PostMapping("/grab")
    public String grab(@RequestHeader("Request-Time")String requestTime,@RequestParam Integer userId,@RequestParam Integer ticketId)  {
        if((Long.parseLong(requestTime) - System.currentTimeMillis()) > 5000){
            return "请求超时，请重试";
        }
        LectureTicketVo lectureTicketVo = ticketGrabService.ticketGrab(ticketId, userId);
        if (ObjectUtil.isEmpty(lectureTicketVo)){
            return "抢票失败，还有下次机会";
        }
        return lectureTicketVo.getCode();
    }

    //当前状态
    @Operation(summary = "用户是否抢到最新宣讲会的票")
    @PostMapping("/status")
    public Boolean status(@RequestBody Integer userId){
        return ticketGrabService.checkRecordExists(userId);
    }


    //没有抢到最新的宣讲会票，展示最新宣讲会页面，定时开放抢票功能
    @Operation(summary = "展示最新宣讲会信息")
    @GetMapping("/showLeastLecture")
    public LectureVo leastLecture(){
        return ticketGrabService.showLeastLecture();
    }
    //已经抢到票了，展示个人二维码
    @Operation(summary = "展示用户个人的宣讲会二维码")
    @PostMapping("/userQRCode")
    public String ticketCode(@RequestBody Integer userId){
        return ticketGrabService.userQRCode(userId);
    }



}
