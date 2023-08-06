package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

/**
 * <p>
 * 面试场次表
 * </p>
 *
 * @author NSC
 * @since 2023-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("interview_position")
public class InterviewPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     startTime
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 面试小组id
     */
    @TableField("group_id")
    private Integer groupId;

    /**
     * 面试地点
     */
    @TableField("location")
    private String location;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 已容纳的人数
     */
    @TableField("contained")
    private Integer contained;

    /**
     * 该地点一小时内可容纳人数
     */
    @TableField("capacity")
    private Integer capacity;

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
