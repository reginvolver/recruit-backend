package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 面试地点表
 * </p>
 *
 * @author cr
 * @since 2023-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("interview_position")
public class InterviewPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 面试地点id

     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 面试地点
     */
    private String location;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 该地点一小时内可容纳人数
     */
    private Integer capacity;

    /**
     * 已容纳的人数
     */
    private Integer contained;

    /**
     * 分配的面试官组别
     */
    private Integer groupId;

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
