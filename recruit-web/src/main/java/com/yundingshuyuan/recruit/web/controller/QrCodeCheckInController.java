package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.enums.RespStatusEnum;
import com.yundingshuyuan.recruit.api.QrCodeCheckInService;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 扫描二维码-接口
 *
 * @author wys
 */
@Slf4j
@RecruitResult
@RestController
@RequestMapping("/checkin")
@RequiredArgsConstructor
public class QrCodeCheckInController {

    private final QrCodeCheckInService qrCodeCheckInService;

    @GetMapping("/qrcode")
    public BasicResultVO<String> getQrCode(@RequestParam String openId, @RequestParam String eventName) {
        return new BasicResultVO<>(RespStatusEnum.SUCCESS, qrCodeCheckInService.createQrCode(openId, eventName));
    }

    @PostMapping("/qrcode")
    public boolean signUserIn(@RequestParam String openId, @RequestBody String scanInfo) {
        qrCodeCheckInService.parseQrCodeInfo(openId, scanInfo);
        return true;
    }

}
