package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cr
 * @Date 2023/8/8 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrabRequestVo {
    /**
     * 抢票用户的id
     */
    private Integer userId;

    /**
     * 宣讲会的id
     */
    private Integer TicketId;

}
