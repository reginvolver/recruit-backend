package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 预约面试表
 * </p>
 *
 * @author cr
 * @since 2023-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("reservation")
public class ReservationPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 预约记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预约面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 预约面试的用户id
     */
    private Integer userId;

    /**
     * 自动分配面试点id
     */
    private Integer interviewId;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
