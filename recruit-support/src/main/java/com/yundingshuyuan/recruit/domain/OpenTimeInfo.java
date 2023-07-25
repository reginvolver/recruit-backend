package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 开放预约面试时间信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("interview_opentime")
@AutoMapper(target = OpenTimeInfoVo.class)
public class OpenTimeInfo {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private long id;
    /**
     * 开放起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField("start_time")
    private LocalDateTime startTime;
    /**
     * 开放结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField("end_time")
    private LocalDateTime endTime;
    /**
     * 容量（计划面试者数量）
     */
    @TableField
    private Integer capacity;
    /**
     * 已预约面试者数量
     */
    @TableField
    private Integer reserved;
    /**
     * 乐观锁
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除
     */
    @TableLogic("false")
    private Boolean deleted;
    /**
     * 记录创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 记录更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
