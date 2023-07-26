package com.yundingshuyuan.recruit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yundingshuyuan.recruit.domain.ApplicationPhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApplicationPhotoMapper extends BaseMapper<ApplicationPhoto> {
    @Select("SELECT COUNT(*) FROM application_photo")
    int countApplicationPhoto();
}