package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

/**
 * <p>
 * 预约面试表
 * </p>
 *
 * @author NSC
 * @since 2023-08-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 预约记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预约面试的用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 预约面试时间
     */
    @TableField("interview_time")
    private LocalDateTime interviewTime;

    /**
     * 自动分配面试地点的id
     */
    @TableField("interview_id")
    private Integer interviewId;

    /**
     * 乐观锁
     */
    @TableField("version")
    @Version
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
