package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yundingshuyuan.recruit.api.ApplicationPhotoService;
import com.yundingshuyuan.recruit.dao.ApplicationPhotoMapper;
import com.yundingshuyuan.recruit.domain.po.ApplicationPhotoPo;
import org.springframework.stereotype.Service;

@Service
public class ApplicationPhotoServiceImpl extends ServiceImpl<ApplicationPhotoMapper, ApplicationPhotoPo> implements ApplicationPhotoService {
}
