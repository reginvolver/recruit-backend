package com.yundingshuyuan.recruit.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsVo {

    /**
     * 新闻id
     */
    @TableId("id")
    private Integer id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8", locale = "zh")
    private Date time;

    /**
     * 点赞数
     */
    private Integer likeNumber;

    /**
     * 浏览数
     */
    private Integer viewNumber;

}
