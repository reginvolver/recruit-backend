package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 面试记录表
 * </p>
 *
 * @author cr
 * @since 2023-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("interview_record")
public class InterviewRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 面试id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 参加面试的用户id
     */
    private Integer userId;

    /**
     * 参加面试的用户name
     */
    private String userName;

    /**
     * 面试情况反馈
     */
    private String faceback;

    /**
     * 面试得分
     */
    private BigDecimal score;

    /**
     * 面试官
     */
    private Integer group_id;


    /**
     * 乐观锁
     */
    @Version
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
