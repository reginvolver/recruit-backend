package com.yundingshuyuan.recruit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yundingshuyuan.recruit.domain.ApplicationPhoto;
import com.yundingshuyuan.recruit.domain.vo.ApplicationPhotoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApplicationPhotoMapper extends BaseMapperPlus<ApplicationPhoto, ApplicationPhotoVo> {
    @Select("SELECT COUNT(*) FROM application_photo")
    int countApplicationPhoto();
}