package com.yundingshuyuan.recruit.api;

/**
 面试结果查询
 */

public interface InterviewRecordService {
    /**
     * 查询面试结果
     * @return
     */
    Integer isInterviewPassed(Integer userId);
}
