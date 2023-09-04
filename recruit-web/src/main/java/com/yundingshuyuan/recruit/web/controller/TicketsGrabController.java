package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.TicketGrabService;
import com.yundingshuyuan.recruit.domain.vo.GrabRequestVo;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.recruit.web.exception.CommonException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 抢票系统
 *
 * @Author cr
 * @Date 2023/8/1 1:46
 */

@Slf4j
@RecruitResult
@Tag(name = "抢票系统")
@RestController
@RequestMapping("/miniapp/ticket")
public class TicketsGrabController {

    @Resource
    TicketGrabService ticketGrabService;

    //返回所有场次的宣讲会信息
    @Operation(summary = "返回所有场次的宣讲会信息")
    @GetMapping("/allLecture")
    public List<LectureVo> allLecture(){
        return ticketGrabService.allLecture();
    }

    //查看某一场宣讲会的详细信息/个人抢到的票中的宣讲会详细信息
    @Operation(summary = "宣讲会详细信息")
    @PostMapping("/detailLecture")
    public LectureVo detailLecture(@RequestParam("lecture_id") Integer lectureId){
        return ticketGrabService.detailLecture(lectureId);
    }

    //用户查看自己已经抢到的票
    @Operation(summary = "用户已经抢到的票")
    @GetMapping("/snatchedTicketByUser")
    public List<LectureVo> snatchedTicket(@RequestParam("user_id") Integer userId){
        return ticketGrabService.allTicketByUser(userId);
    }


    //抢票
    @Operation(summary = "抢票")
    @PostMapping("/grab")
    public boolean grab(@RequestHeader("Request-Time")String requestTime,@RequestBody GrabRequestVo grabRequestVo)  {
        if((Long.parseLong(requestTime) - System.currentTimeMillis()) > 5000){
            throw new CommonException("500","请求超时，请重试");
        }
        return ticketGrabService.ticketGrab(grabRequestVo.getTicketId(), grabRequestVo.getUserId());
    }

}
