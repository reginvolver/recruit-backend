package com.yundingshuyuan.recruit.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * 申请书照片数值对象。
 */
@Data
public class ApplicationPhotoVo {
    /**
     * 应申请书照片的唯一标识符。
     */
    private Integer id;

    /**
     * 与应用照片关联的用户ID。
     */
    private Integer userId;

    /**
     * 申请书照片的URL。
     */
    private String photoUrl;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 表示申请书照片是否已删除。
     */
    private Boolean deleted;

    /**
     * 申请书照片创建的日期和时间。
     */
    private LocalDateTime createTime;

    /**
     * 申请书照片最后更新的日期和时间。
     */
    private LocalDateTime updateTime;
}