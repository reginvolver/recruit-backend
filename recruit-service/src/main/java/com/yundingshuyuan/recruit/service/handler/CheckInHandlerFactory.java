package com.yundingshuyuan.recruit.service.handler;

public interface CheckInHandlerFactory<T> {
    CheckInHandler<T> createCheckInHandler();
}