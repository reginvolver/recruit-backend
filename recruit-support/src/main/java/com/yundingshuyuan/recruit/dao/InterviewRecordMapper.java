package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.InterviewRecordPo;
import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;
import org.apache.ibatis.annotations.Mapper;

/**
 面试结果查询
 */
@Mapper
public interface InterviewRecordMapper extends BaseMapperPlus<InterviewRecordPo, InterviewRecordVo> {
    Integer getUserIdByCloudId(String cloudId);
    InterviewRecordPo getByUserId(Integer userId);
}