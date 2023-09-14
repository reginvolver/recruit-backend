package com.yundingshuyuan.recruit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yundingshuyuan.recruit.api.ApplicationPhotoService;
import com.yundingshuyuan.recruit.api.RegisterInfoService;
import com.yundingshuyuan.recruit.domain.po.ApplicationPhotoPo;
import com.yundingshuyuan.recruit.domain.vo.ApplicationPhotoVo;
import com.yundingshuyuan.recruit.utils.FileUploadUtils;
import io.github.linpeilie.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class RegisterInfoImpl implements RegisterInfoService {

    @Autowired
    private ApplicationPhotoService applicationPhotoService;

    @Autowired
    private Converter converter;


    @Override
    @Transactional
    public String submitApplicationPhoto(MultipartFile file, String cloudId) {
        FileUploadUtils fileUploadUtils = new FileUploadUtils();
        String str = fileUploadUtils.fileUpload(file);
        if (str != null) {
            ApplicationPhotoPo applicationPhoto = new ApplicationPhotoPo(cloudId, str);
            applicationPhotoService.save(applicationPhoto);
            return str;
        }
        return null;
    }

    @Override
    public List<ApplicationPhotoVo> downloadApplicationPhoto(String openId) throws FileNotFoundException {
        List<ApplicationPhotoPo> list = applicationPhotoService.list(new QueryWrapper<ApplicationPhotoPo>()
                .eq("cloud_id", openId));
        return list.stream().map(o -> converter.convert(o, ApplicationPhotoVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deletePhoto(String openId) throws FileNotFoundException {
        return applicationPhotoService.remove(new QueryWrapper<ApplicationPhotoPo>()
                .eq("cloud_id", openId));
    }

}
