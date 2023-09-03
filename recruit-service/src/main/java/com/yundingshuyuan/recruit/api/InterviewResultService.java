package com.yundingshuyuan.recruit.api;



import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;

import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/27 16:08
 */
public interface InterviewResultService {

    /**
     * 数据库InterviewRecord中所有的记录
     * @return
     */
    public List<InterviewRecordVo> showAllRecord();

    /**
     * 传入groupId，查找到它的该面试官对应的记录
     *
     * @param groupId
     * @return
     */
    public List<InterviewRecordVo> showAdminRecord(Integer groupId);
}
