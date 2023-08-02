package com.yundingshuyuan.recruit.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckInHandlerManager {
    private final Map<String, CheckInHandler<?>> handlerMap = new HashMap<>();

    @Autowired
    public CheckInHandlerManager(List<CheckInHandler<?>> checkInHandlers) {
        for (CheckInHandler<?> handler : checkInHandlers) {
            handlerMap.put(handler.getBindingName(), handler);
        }
    }

    /**
     * 动态注册 处理
     *
     * @param handlerBindingName handler 对应的 事件名称
     * @param factory
     * @param <T>
     */
    public <T> void registerCheckInHandler(String handlerBindingName, CheckInHandlerFactory<T> factory) {
        // 动态创建策略实现类
        CheckInHandler<T> checkInHandler = factory.createCheckInHandler();
        handlerMap.put(handlerBindingName, checkInHandler);
    }

    public CheckInHandler getCheckInHandler(String eventName) {
        return handlerMap.get(eventName);
    }

}