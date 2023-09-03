package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class InterviewCheckInPo {
    /**
     * 用户Id
     */
    @TableField("user_id")
    Long userId;
}
