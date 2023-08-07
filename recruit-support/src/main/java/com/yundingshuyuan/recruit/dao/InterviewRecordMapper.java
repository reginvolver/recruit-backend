package com.yundingshuyuan.recruit.dao;


import com.yundingshuyuan.recruit.domain.InterviewRecord;
import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;

/**
 * <p>
 * 面试记录表 Mapper 接口
 * </p>
 *
 * @author cr
 * @since 2023-07-27
 */
public interface InterviewRecordMapper extends BaseMapperPlus<InterviewRecord, InterviewRecordVo> {

    InterviewRecordVo isExistByUserId(Integer userId);

    int updateRecord(InterviewRecordVo interviewRecordVo);

}
