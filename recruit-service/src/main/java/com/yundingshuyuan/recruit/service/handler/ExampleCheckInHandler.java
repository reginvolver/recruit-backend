package com.yundingshuyuan.recruit.service.handler;

import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;
import com.yundingshuyuan.recruit.domain.po.LectureCheckInPo;
import org.springframework.stereotype.Component;

/**
 * Example Checkin Handler
 *
 * @author wys
 */
@Component
public class ExampleCheckInHandler implements CheckInHandler<LectureCheckInPo>, CheckInHandlerFactory<LectureCheckInPo> {

    /**
     * 密码 16 字节
     */
    private static final String PASSWORD = "xxxxxxxxxxxxxxxxxx";

    @Override
    public String getBindingName() {
        return "Example";
    }

    @Override
    public void doCheckIn(CheckInEvent<?> event, QrCodeCheckInMapper mapper) {
        // 签到操作 -> 改具体的信息
    }

    @Override
    public LectureCheckInPo handleByOpenId(String openId, QrCodeCheckInMapper mapper) {
        // 通过 OpenId 返回信息 -> 获取生成二维码需要的信息
        return null;
    }

    @Override
    public String encipher(LectureCheckInPo data, long createTimestamp, long expireTimestamp) {
        //加密算法, 对数据进行加密 -> 获取加密后数据字符串
        return null;
    }

    @Override
    public CheckInEvent decipher(String saltAndEncryptedData) {
        // 加密算法, 对数据进行解密 -> 获取标准 CheckInEvent 对象
        return null;
    }

    @Override
    public CheckInHandler<LectureCheckInPo> createCheckInHandler() {
        return this;
    }
}
