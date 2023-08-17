package com.yundingshuyuan.recruit.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    private Date lectureTime;

    private Integer ticketNumber;
    private Integer ticketRemain;
    private Integer lectureOrder;
    @Version
    private Integer version;
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
