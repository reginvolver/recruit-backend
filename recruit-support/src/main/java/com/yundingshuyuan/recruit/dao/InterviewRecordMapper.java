package com.yundingshuyuan.recruit.dao;


import com.yundingshuyuan.recruit.domain.InterviewRecord;
import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;

import java.util.List;

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

    /**
     * 根据group_id查询面试记录
     * @param group_id
     * @return
     */
    List<InterviewRecordVo> adminRecord(Integer group_id);

}
