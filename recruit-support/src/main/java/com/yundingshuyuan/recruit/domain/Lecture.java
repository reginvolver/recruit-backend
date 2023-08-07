package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 宣讲会表
 * </p>
 *
 * @author cr
 * @since 2023-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lecture")
public class Lecture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 宣讲会id
     */
    @TableId(value = "lecture_id", type = IdType.AUTO)
    private Integer lectureId;

    /**
     * 宣讲人
     */
    private String speaker;

    /**
     * 宣讲主题
     */
    private String lectureTheme;

    /**
     * 宣讲时间
     */
    private LocalDateTime lectureTime;

    /**
     * 参会人数
     */
    private Integer ticketNumber;

    /**
     * 剩余票数
     */
    private Integer ticketRemain;

    /**
     * 宣讲会排序
     */
    private Integer lectureOrder;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 逻辑删除
     */
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
