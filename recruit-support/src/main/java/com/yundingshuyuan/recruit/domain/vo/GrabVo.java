package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抢票返回信息
 *
 * @Author cr
 * @Date 2023/8/5 17:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrabVo {
    /**
     *
     * 抢票结果信息 成功/失败
     */
    private String grabMessage;
    /**
     *
     * 二维码
     */
    private String qrCode;
}
