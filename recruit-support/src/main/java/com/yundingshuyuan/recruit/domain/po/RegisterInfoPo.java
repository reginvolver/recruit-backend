package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 报名表
 */
@Data
@TableName("register_info")
public class RegisterInfoPo {
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