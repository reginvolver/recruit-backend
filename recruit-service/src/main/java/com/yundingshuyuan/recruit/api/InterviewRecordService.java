package com.yundingshuyuan.recruit.api;

import cn.dev33.satoken.annotation.SaCheckPermission;

/**
 面试结果查询
 */

public interface InterviewRecordService {
    /**
     * 查询面试结果
     * @param cloudId 用户微信的cloud_id
     * @return
     */
    @SaCheckPermission("user:isInterviewPassed")
    boolean isInterviewPassed(String cloudId);
}
