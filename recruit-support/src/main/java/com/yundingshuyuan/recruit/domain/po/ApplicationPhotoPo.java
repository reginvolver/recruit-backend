package com.yundingshuyuan.recruit.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yundingshuyuan.recruit.domain.vo.ApplicationPhotoVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 申请书照片数值对象。
 */
@Data
@TableName("application_photo")
@AutoMapper(target = ApplicationPhotoVo.class)
public class ApplicationPhotoPo {
    /**
     * 应申请书照片的唯一标识符。
     */
    private Integer id;

    /**
     * 与应用照片关联的用户ID。
     */
    private String cloudId;

    /**
     * 申请书照片的URL。
     */
    @TableField("url")
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

    public ApplicationPhotoPo (String id,String url){
        this.cloudId = id;
        this.photoUrl = url;
    }
}
