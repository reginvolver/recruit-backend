package com.yundingshuyuan.recruit.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *报名表
 */
@Data
public class RegisterInfoVo {
    /**
     主键ID
     */
    private Integer id;
    /**
     用户ID
     */
    private Integer userId;
    /**
     报名方向
     */
    private String direction;
    /**
     乐观锁
     */
    private Integer version;
    /**
     是逻辑删除
     */
    private Boolean deleted;
    /**
     创建时间
     */
    private LocalDateTime createTime;
    /**
     更新时间
     */
    private LocalDateTime updateTime;

}