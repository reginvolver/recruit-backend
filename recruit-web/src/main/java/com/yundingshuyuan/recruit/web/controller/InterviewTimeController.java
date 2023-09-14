package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.InterviewTimeService;
import com.yundingshuyuan.recruit.domain.po.OpenTimeInfoPo;
import com.yundingshuyuan.recruit.domain.po.ReservationPo;
import com.yundingshuyuan.recruit.domain.vo.ReservationVo;
import com.yundingshuyuan.recruit.domain.vo.ReserveInterviewTimeVo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RecruitResult
@RestController
@Tag(name = "预约面试时间接口")
@RequestMapping("/miniapp/interviewTime")
public class InterviewTimeController {

    @Autowired
    private InterviewTimeService interviewTimeService;

    /**
     * 预约面试时间
     *
     * @return
     */
    @Operation(summary = "预约面试时间")
    @PostMapping("/reserve-interview")
    public Integer reserveInterview(@RequestBody ReserveInterviewTimeVo reservationVo) {
        Integer userId = reservationVo.getUserId();
        LocalDateTime startTime = reservationVo.getInterviewTime();
        return interviewTimeService.reserveInterview(userId, startTime);

    }

    /**
     * 查询所有面试时间段
     *
     * @return
     */
    @Operation(summary = "展示所有面试时间段")
    @GetMapping("/allInterviewTimes")
    public List<OpenTimeInfoPo> getAllInterviewTimes() {
        return interviewTimeService.getAllInterviewTimes();
    }

    /**
     * 查询已预约的面试时间
     */
    @Operation(summary = "展示已预约的面试时间段")
    @GetMapping("/reservations")
    public List<ReservationPo> getReservationsByUserId(@RequestParam("userId") int userId) {
        return interviewTimeService.getReservationsByUserId(userId);
    }
}