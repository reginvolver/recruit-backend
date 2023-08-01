package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 宣讲会签到
 *
 * @author wys
 */
@Data
@TableName("lecture_ticket")
public class LectureCheckInPo {
    /**
     * 用户Id
     */
    @TableField("user_id")
    Long userId;
    /**
     * 宣讲会编号
     */
    @TableField("lecture_id")
    Long lectureId;
}
