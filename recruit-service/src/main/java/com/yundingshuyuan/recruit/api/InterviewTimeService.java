package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.po.OpenTimeInfoPo;

import java.time.LocalDateTime;
import java.util.List;

/**
 预约面试时间
 */
public interface InterviewTimeService {

    /**
     * 预约面试时间
     * @param startTime 选择的面试时间段的开始时间
     * @return
     */
    Integer reserveInterview(Integer userId, LocalDateTime startTime);
    /**
     * 查询所有面试时间段
     *
     * @return
     */
    List<OpenTimeInfoPo>  getAllInterviewTimes();
}
