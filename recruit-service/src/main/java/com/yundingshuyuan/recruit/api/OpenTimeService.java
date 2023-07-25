package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.OpenTimeInfo;

/**
 * 预约面试开放时间管理
 */
public interface OpenTimeService {

    /**
     * 设置预约开放时间
     * @return
     */
    boolean setOpenTime(OpenTimeInfo info);
}
