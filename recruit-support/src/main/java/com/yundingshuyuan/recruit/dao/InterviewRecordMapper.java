package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.InterviewRecordPo;
import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 面试结果查询
 */
@Mapper
public interface InterviewRecordMapper extends BaseMapperPlus<InterviewRecordPo, InterviewRecordVo> {
    Integer getUserIdByCloudId(String cloudId);
    InterviewRecordPo getByUserId(Integer userId);

    InterviewRecordVo isExistByUserId(Integer userId);

    int updateRecord(InterviewRecordVo interviewRecordVo);

    /**
     * 根据group_id查询面试记录
     * @param group_id
     * @return
     */
    List<InterviewRecordVo> adminRecord(Integer group_id);
}