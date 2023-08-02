package com.yundingshuyuan.recruit.service.handler;

public interface CheckInHandlerFactory<T> {

    /**
     * 创建 CheckInHandler -> 注册用
     *
     * @return Handler
     */
    CheckInHandler<T> createCheckInHandler();
}