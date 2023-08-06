package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInEventVo {
    /**
     * 事件代号
     */
    String eventName;
    /**
     * 加密事件
     */
    String encryptedData;
}
