package com.yundingshuyuan.recruit.service.handler;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;
import com.yundingshuyuan.recruit.domain.po.InterviewCheckInPo;
import com.yundingshuyuan.recruit.domain.po.LectureCheckInPo;
import com.yundingshuyuan.recruit.domain.vo.CheckInEventVo;
import com.yundingshuyuan.vo.BasicResultVO;
import io.github.linpeilie.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


/**
 * 面试签到策略
 *
 * @author wys
 */
@Component
public class InterviewCheckInHandler implements CheckInHandler<InterviewCheckInPo>, CheckInHandlerFactory<InterviewCheckInPo> {

    /**
     * 密码 32 字节
     */
    static final String PASSWORD = "+-YD_MianShi=)YunDing2023.!*%010";
    @Autowired
    private Converter converter;

    @Override
    public BasicResultVO<Boolean> doCheckIn(CheckInEvent<?> event, QrCodeCheckInMapper mapper) {
        InterviewCheckInPo data = JSON.parseObject(event.getData().toString(), InterviewCheckInPo.class);
        Long userId = data.getUserId();
        // 面试暂时没有无重复面试限制，考虑到有AB面
        mapper.updateStatusByUserId(userId);
        return BasicResultVO.success(true);
    }

    @Override
    public String getBindingName() {
        return "面试";
    }

    @Override
    public InterviewCheckInPo handleByOpenId(String openId, QrCodeCheckInMapper mapper) {
        return mapper.selectInterviewInfoByOpenId(openId);
    }

    @Override
    public String encipher(InterviewCheckInPo data, long createTimestamp, long expireTimestamp) {
        // 获取盐值
        byte[] salt = new byte[16];
        System.arraycopy(SecureUtil.generateKey("AES", 128).getEncoded(), 0, salt, 0, 16);
        // 使用盐值和密码生成密钥
        AES aes = new AES(Mode.CTS, Padding.PKCS5Padding, PASSWORD.getBytes(), salt);
        CheckInEvent event = CheckInEvent.builder()
                .data(data)
                .createTimestamp(createTimestamp)
                .expireTimestamp(expireTimestamp).build();
        String encryptedEvent = aes.encryptHex(event.toString());
        String saltAndEncryptedData = Base64.encode(salt) + Base64.encode(encryptedEvent, StandardCharsets.UTF_8);
        return JSONUtil.toJsonStr(new CheckInEventVo(getBindingName(), saltAndEncryptedData));
    }

    @Override
    public CheckInEvent decipher(String saltAndEncryptedData) {
        // 取出
        byte[] saltFromStr = Base64.decode(saltAndEncryptedData.substring(0, 24));
        String encryptedDataFromStr = Base64.decodeStr(saltAndEncryptedData.substring(24));
        // 使用盐值和密码生成密钥
        AES aes = new AES(Mode.CTS, Padding.PKCS5Padding, PASSWORD.getBytes(), saltFromStr);
        return JSONUtil.toBean(aes.decryptStr(encryptedDataFromStr), CheckInEvent.class);
    }

    @Override
    public CheckInHandler<InterviewCheckInPo> createCheckInHandler() {
        return this;
    }
}
