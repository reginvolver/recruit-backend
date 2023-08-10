package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yundingshuyuan.recruit.api.ApplicationPhotoService;
import com.yundingshuyuan.recruit.api.RegisterInfoService;
import com.yundingshuyuan.recruit.dao.RegisterInfoMapper;
import com.yundingshuyuan.recruit.domain.ApplicationPhoto;
import com.yundingshuyuan.recruit.domain.RegisterInfo;
import com.yundingshuyuan.recruit.domain.vo.RegisterInfoVO;
import com.yundingshuyuan.recruit.utils.FileUploadUtils;
import io.github.linpeilie.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class RegisterInfoImpl implements RegisterInfoService {
    @Autowired
    private RegisterInfoMapper registerInfoMapper;
    @Autowired
    private Converter converter;
    

    @Autowired
    private ApplicationPhotoService applicationPhotoService;

    @Override
    public boolean saveRegisterInfo(RegisterInfoVO registerInfoVO) {
        if (isExist(registerInfoVO.getUserId())) {
            return false;
        }
        RegisterInfo registerInfo = converter.convert(registerInfoVO, RegisterInfo.class);
        int insert = registerInfoMapper.insert(registerInfo);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRegisterInfo(RegisterInfoVO registerInfoVO) {
        RegisterInfo registerInfo = converter.convert(registerInfoVO, RegisterInfo.class);
        LambdaQueryWrapper<RegisterInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RegisterInfo::getUserId, registerInfo.getUserId());
        int update = registerInfoMapper.update(registerInfo, queryWrapper);
        if (update > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public String submitApplicationPhoto(MultipartFile file, Integer id) {
        FileUploadUtils fileUploadUtils = new FileUploadUtils();
        String str = fileUploadUtils.fileUpload(file);
        if (str != null) {
            ApplicationPhoto applicationPhoto = new ApplicationPhoto(id, str);
            applicationPhotoService.save(applicationPhoto);
            return str;
        }
        return null;
    }

    public boolean isExist(Integer id) {
        LambdaQueryWrapper<RegisterInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RegisterInfo::getUserId, id);
        RegisterInfo registerInfo = registerInfoMapper.selectOne(queryWrapper);
        if (registerInfo != null) {
            return true;
        }
        return false;
    }
}
