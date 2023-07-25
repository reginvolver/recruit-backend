package com.yundingshuyuan.recruit.domain;

import lombok.Data;

import java.util.Date;

/**
 * 申请书报名表
 */
@Data
public class ApplicationPhoto {
    private Integer id;
    private String url;
    private Integer userId;
    private Integer version;
    private Boolean deleted;
    private Date createTime;
    private Date updateTime;

    // getter 和 setter 方法省略
}