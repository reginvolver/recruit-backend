package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yundingshuyuan.recruit.api.AcademyService;
import com.yundingshuyuan.recruit.dao.AcademyMapper;
import com.yundingshuyuan.recruit.domain.Academy;
import org.springframework.stereotype.Service;

@Service
public class AcademyServiceImpl extends ServiceImpl<AcademyMapper, Academy> implements AcademyService {
}
