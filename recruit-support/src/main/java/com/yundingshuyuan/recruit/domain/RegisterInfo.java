package com.yundingshuyuan.recruit.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 报名表
 */
@Data
public class RegisterInfo {
    private Integer id;
    private Integer userId;
    private String direction;
    private Integer version;
    private Boolean deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}