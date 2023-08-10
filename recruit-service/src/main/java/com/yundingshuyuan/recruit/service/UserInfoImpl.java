package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.yundingshuyuan.recruit.api.AcademyService;
import com.yundingshuyuan.recruit.api.UserInfoService;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVO;
import com.yundingshuyuan.recruit.service.verify.AbstractUserInfoValidation;
import com.yundingshuyuan.recruit.service.verify.EmailValidation;
import com.yundingshuyuan.recruit.service.verify.PhoneValidation;
import com.yundingshuyuan.recruit.service.verify.StudentNumberValidation;
import com.yundingshuyuan.recruit.utils.QrCodeUtils;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userMapper;

    @Autowired
    private Converter converter;

    @Autowired
    private AcademyService academyService;

    @Override
    @Transactional
    public UserInfoVO showUserInfo(Integer cloudId) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getCloudId, cloudId);
        //根据id查询用户信息，但此时用户信息不包含学校和书院，只有书院id
        UserInfo userInfo = userMapper.selectOne(queryWrapper);
        //根据id查询书院信息
        Academy academy = academyService.getById(userInfo.getAcademyId());
        //user_info中不包含学校和书院的名称，需要在user_infoVO中添加school和academy
        //先转型，再赋值
        UserInfoVO userInfoVO = converter.convert(userInfo, UserInfoVO.class);
        userInfoVO.setAcademy(academy.getAcademy());
        userInfoVO.setSchool(academy.getSchool());
        return userInfoVO;
    }


    @Override
    public boolean updateUserInfo(UserInfoVO userInfoVO) {
        UserInfo userInfo = converter.convert(userInfoVO, UserInfo.class);
        commonValidation(userInfo);
        Integer integer = academyConvert(userInfoVO.getAcademy());
        userInfo.setAcademyId(integer);
        LambdaUpdateChainWrapper<UserInfo> updateWrapper = new LambdaUpdateChainWrapper<>(userMapper);
        boolean update = updateWrapper.eq(UserInfo::getId, userInfo.getId())
                .set(userInfo.getAcademyId() != null, UserInfo::getAcademyId, userInfo.getAcademyId())
                .set(!userInfo.getEmail().isEmpty(), UserInfo::getEmail, userInfo.getEmail())
                .set(!userInfo.getGender().isEmpty(), UserInfo::getGender, userInfo.getGender())
                .set(!userInfo.getMajor().isEmpty(), UserInfo::getMajor, userInfo.getMajor())
                .set(!userInfo.getName().isEmpty(), UserInfo::getName, userInfo.getName())
                .set(!userInfo.getPhone().isEmpty(), UserInfo::getPhone, userInfo.getPhone())
                .set(!userInfo.getQq().isEmpty(), UserInfo::getQq, userInfo.getQq())
                .set(!userInfo.getStudentNumber().isEmpty(), UserInfo::getStudentNumber, userInfo.getStudentNumber())
                .update();
        return update;
    }

    @Override
    public boolean saveUserInfo(UserInfoVO userInfoVO) {
        UserInfo userInfo = converter.convert(userInfoVO, UserInfo.class);
        commonValidation(userInfo);
        Integer integer = academyConvert(userInfoVO.getAcademy());
        userInfo.setAcademyId(integer);
        if (check(userInfo)) {
            String qrCodeBase64 = QrCodeUtils.getQrCodeBase64(userInfo.getCloudId().toString());
            userInfo.setQrCode(qrCodeBase64);
            userMapper.insert(userInfo);
            return true;
        }
        return false;
    }

    public boolean check(UserInfo userInfo) {
        UserInfo userInfo1 = userMapper.selectById(userInfo.getId());
        if (userInfo1 == null) {
            return true;
        }
        return false;
    }

    public Integer academyConvert(String academyName) {
        if (!academyName.isEmpty()) {
            LambdaQueryWrapper<Academy> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Academy::getAcademy, academyName);
            Academy one = academyService.getOne(queryWrapper);
            return one.getId();
        }
        return null;
    }

    /**
     * 常用参数校验
     *
     * @param info
     */
    private void commonValidation(UserInfo info) {
        new AbstractUserInfoValidation.Builder().add(new EmailValidation(info.getEmail()))
                .add(new PhoneValidation(info.getPhone()))
                .add(new StudentNumberValidation(info.getStudentNumber())).build().validate();
    }
}
