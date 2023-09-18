package com.yundingshuyuan.recruit.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("lecture")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Lecture {
    @TableId(value = "lecture_id", type = IdType.AUTO)
    private Integer lectureId;
    private String speaker;
    private String lectureTheme;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8", locale = "zh")
    //@JsonSerialize(using = DateSerializer.class)
    //@JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime lectureTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8", locale = "zh")
    private LocalDateTime grabTime;
    private Integer ticketNumber;
    private Integer ticketRemain;
    private Integer lectureOrder;
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
