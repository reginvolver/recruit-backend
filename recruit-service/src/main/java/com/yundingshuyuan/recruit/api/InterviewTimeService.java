package com.yundingshuyuan.recruit.api;

import cn.dev33.satoken.annotation.SaCheckPermission;

import java.time.LocalDateTime;

/**
 预约面试时间
 */
public interface InterviewTimeService {

    /**
     * 预约面试时间
     * @param cloudId 用户微信的cloud_id
     * @param startTime 选择的面试时间段的开始时间
     * @return
     */
    @SaCheckPermission("user:reserveInterview")
    boolean reserveInterview(String cloudId, LocalDateTime startTime);
}
