package com.yundingshuyuan.recruit.domain.vo;

import com.yundingshuyuan.recruit.domain.ApplicationPhoto;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = ApplicationPhoto.class)
public class ApplicationPhotoVO {

    private Integer id;
    private String url;
    private Integer user_id;
}
