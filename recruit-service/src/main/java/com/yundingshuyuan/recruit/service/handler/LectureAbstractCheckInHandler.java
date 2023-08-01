package com.yundingshuyuan.recruit.service.handler;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;
import com.yundingshuyuan.recruit.domain.CheckInEventWrapper;
import com.yundingshuyuan.recruit.domain.po.LectureCheckInPo;
import org.springframework.stereotype.Component;


/**
 * 宣讲会签到策略
 *
 * @author wys
 */
@Component
public class LectureAbstractCheckInHandler implements CheckInHandler<LectureCheckInPo>, CheckInHandlerFactory<LectureCheckInPo> {

    /**
     * 密钥 32 字节
     */
    static final String KEY = "+-YD_Lecture=)YunDing2023.!*%010";

    @Override
    public void doCheckIn(CheckInEvent event, QrCodeCheckInMapper mapper) {
        LectureCheckInPo data = JSONUtil.toBean((JSONObject) event.getData(), LectureCheckInPo.class);
        mapper.checkInLectureByUserId(data.getUserId());
    }

    @Override
    public String getName() {
        return "宣讲会";
    }

    @Override
    public LectureCheckInPo handleByOpenId(String openId, QrCodeCheckInMapper mapper) {
        return mapper.selectTicket(openId);
    }

    @Override
    public String encipher(LectureCheckInPo data) {
        CheckInEvent event = new CheckInEvent();
        event.setData(data);
        String encryptedEvent = SecureUtil.des(KEY.getBytes()).encryptHex(event.toString());
        return JSONUtil.toJsonStr(new CheckInEventWrapper(getName(), encryptedEvent));
    }

    @Override
    public CheckInEvent decipher(String data) {
        return JSONUtil.toBean(SecureUtil.des(KEY.getBytes()).decryptStr(data), CheckInEvent.class);
    }

    @Override
    public CheckInHandler<LectureCheckInPo> createCheckInHandler() {
        return this;
    }
}
