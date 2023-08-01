package com.yundingshuyuan.recruit.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckInHandlerManager {
    private final Map<String, CheckInHandler<?>> HANDLER_MAPPER = new HashMap<>();

    @Autowired
    public CheckInHandlerManager(List<CheckInHandler<?>> checkInHandlers) {
        for (CheckInHandler<?> handler : checkInHandlers) {
            HANDLER_MAPPER.put(handler.getName(), handler);
        }
    }

    public <T> void registerCheckInHandler(String eventName, CheckInHandlerFactory<T> factory) {
        // 动态创建策略实现类
        CheckInHandler<T> checkInHandler = factory.createCheckInHandler();
        HANDLER_MAPPER.put(eventName, checkInHandler);
    }

    public CheckInHandler<?> getCheckInHandler(String eventName) {
        return HANDLER_MAPPER.get(eventName);
    }

}