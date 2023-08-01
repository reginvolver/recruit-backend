package com.yundingshuyuan.recruit.service.handler;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;
import com.yundingshuyuan.recruit.domain.CheckInEventWrapper;
import com.yundingshuyuan.recruit.domain.po.LectureCheckInPo;
import org.springframework.stereotype.Component;

/**
 * 面试时签到策略
 *
 * @author wys
 */
@Component
public class InterviewAbstractCheckInHandler implements CheckInHandler<LectureCheckInPo>, CheckInHandlerFactory<LectureCheckInPo> {

    /**
     * 密钥 32 字节
     */
    private static final String KEY = "+-YD_Interview=)YunDing2023.!*%0";

    @Override
    public String getName() {
        return "面试";
    }

    @Override
    public void doCheckIn(CheckInEvent<LectureCheckInPo> event, QrCodeCheckInMapper mapper) {

    }

    @Override
    public LectureCheckInPo handleByOpenId(String openId, QrCodeCheckInMapper mapper) {
        return null;
    }

    @Override
    public String encipher(LectureCheckInPo data) {
        CheckInEvent<LectureCheckInPo> event = new CheckInEvent<>();
        event.setData(data);
        String encryptedEvent = SecureUtil.aes(KEY.getBytes()).encryptHex(event.toString());
        return JSONUtil.toJsonStr(new CheckInEventWrapper(getName(), encryptedEvent));
    }

    @Override
    public CheckInEvent decipher(String data) {
        return JSONUtil.toBean(SecureUtil.aes(KEY.getBytes()).decryptStr(data), CheckInEvent.class);
    }

    @Override
    public CheckInHandler<LectureCheckInPo> createCheckInHandler() {
        return this;
    }
}
