package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.ApplicationPhotoPo;
import com.yundingshuyuan.recruit.domain.vo.ApplicationPhotoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationPhotoMapper extends BaseMapperPlus<ApplicationPhotoPo, ApplicationPhotoVo> {

    int countApplicationPhoto();

}
