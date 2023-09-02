package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yundingshuyuan.recruit.api.ApplicationPhotoService;
import com.yundingshuyuan.recruit.api.RegisterInfoService;
import com.yundingshuyuan.recruit.dao.RegisterInfoMapper;
import com.yundingshuyuan.recruit.domain.po.ApplicationPhotoPo;
import com.yundingshuyuan.recruit.domain.po.RegisterInfoPo;
import com.yundingshuyuan.recruit.domain.vo.RegisterInfoVo;
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
    public boolean saveRegisterInfo(RegisterInfoVo registerInfoVO) {
        if (isExist(registerInfoVO.getUserId())) {
            return false;
        }
        RegisterInfoPo registerInfo = converter.convert(registerInfoVO, RegisterInfoPo.class);
        int insert = registerInfoMapper.insert(registerInfo);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRegisterInfo(RegisterInfoVo registerInfoVO) {
        RegisterInfoPo registerInfo = converter.convert(registerInfoVO, RegisterInfoPo.class);
        LambdaUpdateWrapper<RegisterInfoPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RegisterInfoPo::getUserId, registerInfo.getUserId());
        int update = registerInfoMapper.update(registerInfo, updateWrapper);
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
            ApplicationPhotoPo applicationPhoto = new ApplicationPhotoPo(id, str);
            applicationPhotoService.save(applicationPhoto);
            return str;
        }
        return null;
    }

    public boolean isExist(Integer id) {
        LambdaQueryWrapper<RegisterInfoPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RegisterInfoPo::getUserId, id);
        RegisterInfoPo registerInfo = registerInfoMapper.selectOne(queryWrapper);
        if (registerInfo != null) {
            return true;
        }
        return false;
    }
}
