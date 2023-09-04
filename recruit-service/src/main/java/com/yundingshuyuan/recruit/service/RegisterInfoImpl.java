package com.yundingshuyuan.recruit.service;


import com.yundingshuyuan.recruit.api.ApplicationPhotoService;
import com.yundingshuyuan.recruit.api.RegisterInfoService;
import com.yundingshuyuan.recruit.domain.po.ApplicationPhotoPo;
import com.yundingshuyuan.recruit.utils.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class RegisterInfoImpl implements RegisterInfoService {



    @Autowired
    private ApplicationPhotoService applicationPhotoService;


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


}
