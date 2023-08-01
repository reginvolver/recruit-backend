package com.yundingshuyuan.recruit.service;

import cn.hutool.json.JSONUtil;
import com.yundingshuyuan.recruit.api.QrCodeCheckInService;
import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;
import com.yundingshuyuan.recruit.domain.CheckInEventWrapper;
import com.yundingshuyuan.recruit.service.handler.CheckInHandler;
import com.yundingshuyuan.recruit.service.handler.CheckInHandlerManager;
import com.yundingshuyuan.recruit.utils.QrCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 二维码签到相关 Service
 *
 * @author Stayw33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeCheckInCheckInServiceImpl implements QrCodeCheckInService {

    private final QrCodeCheckInMapper qrCheckMapper;

    private final CheckInHandlerManager ciHandlerManager;

    @Override
    public void parseQrCodeInfo(String openId, String scanInfo) {
        CheckInEventWrapper wrapper = JSONUtil.toBean(scanInfo, CheckInEventWrapper.class);
        // 根据事件名调用
        CheckInHandler checkinHandler = ciHandlerManager.getCheckInHandler(wrapper.getEventName());
        // 解密被加密事件
        CheckInEvent event = checkinHandler.decipher(wrapper.getEncryptedEvent());
        // 操作
        checkinHandler.doCheckIn(event, qrCheckMapper);
    }

    @Override
    public String createQrCode(String openId, String eventName) {
        // 创建 事件所需信息
        CheckInHandler checkinHandler = ciHandlerManager.getCheckInHandler(eventName);
        Object data = checkinHandler.handleByOpenId(openId, qrCheckMapper);
        String content = checkinHandler.encipher(data);
        return QrCodeUtils.getQrCodeBase64(content);
    }

}
