package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

/**
 * 面试地点表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewPosition {

    /**
     * 面试地点 id
     */
    @TableId
    private Long id;
    /**
     * 面试场地
     */
    @TableField
    private String location;
    /**
     * 开放起始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 开放结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 场地容纳量(面试者)
     */
    @TableField
    private Integer capacity;
    /**
     * 乐观锁
     */
    @TableField
    private Integer version;
    /**
     * 逻辑删除
     */
    @TableField
    private Boolean deleted;
    /**
     * 记录创建时间
     */
    @TableField
    private Date createTime;
    /**
     * 记录更新时间
     */
    @TableField
    private Date updateTime;

}
