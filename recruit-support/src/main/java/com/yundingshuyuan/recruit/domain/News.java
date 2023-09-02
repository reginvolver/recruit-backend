package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * (News)实体类
 *
 * @author makejava
 * @since 2023-07-31 18:31:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("news")
public class News {
    /**
     * 新闻id
     */
    @TableId("id")
    private Integer id;

    /**
     * 书院id
     */
    private Integer academyId;

    /**
     * 新闻标题
     */
    private String title;
    /**
     * 时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;
    /**
     * 点赞数
     */
    private Integer likeNumber;
    /**
     * 浏览数
     */
    private Integer viewNumber;
    /**
     * 逻辑删除
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 乐观锁
     */
    @Version
    private Integer version;

}

