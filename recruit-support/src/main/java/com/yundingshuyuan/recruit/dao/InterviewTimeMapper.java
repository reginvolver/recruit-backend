package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.OpenTimeInfoPo;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 预约面试时间
 */
@Mapper
public interface InterviewTimeMapper extends BaseMapperPlus<OpenTimeInfoPo, OpenTimeInfoVo> {

    OpenTimeInfoPo getInterviewTimeByStartTime(LocalDateTime startTime);

    void updateByInterviewTime(OpenTimeInfoPo interviewTime);
}