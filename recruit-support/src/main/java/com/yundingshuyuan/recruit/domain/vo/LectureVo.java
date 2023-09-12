package com.yundingshuyuan.recruit.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureVo {
    /**
     * 宣讲会id
     */
    private Integer lectureId;

    /**
     * 宣讲人
     */
    private String speaker;

    /**
     * 宣讲主题
     */
    private String lectureTheme;
    //@JSONField(format ="yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8", locale = "zh")
    private Date lectureTime;
    /**
     * 宣讲内容
     */
    @TableField("content_introduction")
    private String contentIntroduction;
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
}
