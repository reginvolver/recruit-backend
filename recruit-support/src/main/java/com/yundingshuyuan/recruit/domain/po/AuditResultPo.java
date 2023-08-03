package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.yundingshuyuan.recruit.domain.vo.AuditResultVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("interview_record")
@AutoMapper(target = AuditResultVo.class)
public class AuditResultPo {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    long id;
    /**
     * 面试者
     */
    @TableField("user_name")
    String interviewee;
    /**
     * 分数
     */
    @TableField
    BigDecimal score;
    /**
     * 学号
     */
    @TableField("student_number")
    long studentId;
    /**
     * 面试官组别
     */
    @TableField
    long groupId;
    /**
     * 是否通过
     */
    @TableField
    boolean passed;
    /**
     * 乐观锁
     */
    @Version
    private Integer version = 1;
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
