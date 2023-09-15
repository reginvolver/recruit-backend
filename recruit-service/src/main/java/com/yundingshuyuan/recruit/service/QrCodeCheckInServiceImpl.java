package com.yundingshuyuan.recruit.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yundingshuyuan.constant.CommonConstant;
import com.yundingshuyuan.enums.CheckInRespStatusEnum;
import com.yundingshuyuan.enums.RespStatusEnum;
import com.yundingshuyuan.recruit.api.QrCodeCheckInService;
import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;
import com.yundingshuyuan.recruit.domain.vo.CheckInEventVo;
import com.yundingshuyuan.recruit.service.handler.CheckInHandler;
import com.yundingshuyuan.recruit.service.handler.CheckInHandlerManager;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 二维码签到相关 Service
 *
 * @author wys
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeCheckInServiceImpl implements QrCodeCheckInService {

    private final QrCodeCheckInMapper qrCheckMapper;

    private final CheckInHandlerManager ciHandlerManager;

    /*private final QrCodeUtils qrCodeUtils;*/

    @Override
    public BasicResultVO<String> createQrCode(String openId, String eventName, int expireTime) {
        // 创建 事件所需信息
        CheckInHandler checkinHandler = ciHandlerManager.getCheckInHandler(eventName);
        Object data = checkinHandler.handleByOpenId(openId, qrCheckMapper);
        // data 校验
        if (data == null) {
            return new BasicResultVO<>(CheckInRespStatusEnum.OPENID_NOT_FOUND);
        }
        // 创建时间
        long createTimestamp = System.currentTimeMillis();
        long expireTimestamp = createTimestamp + expireTime * 1000L;
        // 加密
        try {
            return BasicResultVO.success(checkinHandler.encipher(data, createTimestamp, expireTimestamp));
            /*return BasicResultVO.success((qrCodeUtils.getQrCodeBase64(content));*/
        } catch (Exception e) {
            log.error(CheckInRespStatusEnum.ENCIPHER_FAIL.getMsg() + e.getMessage());
            return new BasicResultVO<>(CheckInRespStatusEnum.ENCIPHER_FAIL);
        }
    }

    @Override
    public BasicResultVO<CheckInEvent> parseQrCodeInfo(String scanInfo) {
        CheckInEventVo wrapper;
        // 根据事件名调用
        CheckInHandler<?> checkinHandler;
        // 待解密被加密事件信息
        CheckInEvent<?> event;
        // 解析异常
        try {
            wrapper = JSON.parseObject(scanInfo, CheckInEventVo.class);
        } catch (Exception e) {
            log.error(RespStatusEnum.JSON_PARSE_ERROR + e.getMessage());
            return BasicResultVO.fail(RespStatusEnum.JSON_PARSE_ERROR);
        }
        // 根据事件名调用 可能获取不到对应的处理器
        checkinHandler = ciHandlerManager.getCheckInHandler(wrapper.getEventName());
        if (checkinHandler == null) {
            log.error(CheckInRespStatusEnum.CHECKIN_HANDLER_NOT_EXIST.getMsg());
            return new BasicResultVO<>(CheckInRespStatusEnum.CHECKIN_HANDLER_NOT_EXIST);
        }
        // 解密被加密事件信息
        try {
            event = checkinHandler.decipher(wrapper.getEncryptedData());
        } catch (Exception e) {
            log.error(RespStatusEnum.JSON_PARSE_ERROR + e.getMessage());
            return BasicResultVO.fail(RespStatusEnum.JSON_PARSE_ERROR);
        }
        // 判断二维码是否过期
        if (isExpired(event)) {
            return new BasicResultVO<>(CheckInRespStatusEnum.QRCODE_EXPIRED);
        }
        // 根据事件信息操作
        checkinHandler.doCheckIn(event, qrCheckMapper);
        // 日志
        log.info("签到事件成功:CurrentTime{}", LocalDateTime.now());
        return new BasicResultVO<>(CheckInRespStatusEnum.CHECKIN_SUCCESS, event);
    }

    /**
     * 检验二维码是否有效
     * @param event
     */
    private static boolean isExpired(CheckInEvent<?> event) {
        // 判断二维码是否过期
        long expireTimestamp = event.getExpireTimestamp();
        long serviceTime = System.currentTimeMillis();
        if (serviceTime > expireTimestamp) {
            log.error(StrUtil.format("\n錯誤[error]:\n二维码失效:at {} \n 当前服务器时间:currentTime {}",
                    DateUtil.format(new Date(expireTimestamp), CommonConstant.DATE_TIME_FORMAT_YMDHMSS),
                    DateUtil.format(new Date(serviceTime), CommonConstant.DATE_TIME_FORMAT_YMDHMSS)));
            return true;
        }
        return false;
    }


}
