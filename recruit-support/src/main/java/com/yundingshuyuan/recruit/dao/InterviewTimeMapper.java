package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.OpenTimeInfoPo;
import com.yundingshuyuan.recruit.domain.po.ReservationPo;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 预约面试时间
 */
@Mapper
public interface InterviewTimeMapper extends BaseMapperPlus<OpenTimeInfoPo, OpenTimeInfoVo> {

    OpenTimeInfoPo getInterviewTimeByStartTime(LocalDateTime startTime);

    void updateByInterviewTime(OpenTimeInfoPo interviewTime);

    List<OpenTimeInfoPo> getAllInterviewTimes();

    void updateReservedCount(@Param("interviewTimeId") int interviewTimeId, @Param("reservedCount") int reservedCount);

    List<ReservationPo> getReservationsByUserId(@Param("userId") int userId);
}