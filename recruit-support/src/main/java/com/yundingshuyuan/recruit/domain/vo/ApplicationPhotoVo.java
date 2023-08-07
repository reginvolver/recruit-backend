package com.yundingshuyuan.recruit.domain.vo;

import com.yundingshuyuan.recruit.domain.ApplicationPhoto;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cr
 * @Date 2023/7/27 15:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = ApplicationPhoto.class)
public class ApplicationPhotoVo {
    /**
     * 申请书id
     */
    private Integer id;

    /**
     * 申请书保存地址
     */
    private String url;

    /**
     * 与报名信息表id相关联
     */
    private Integer userId;
}
