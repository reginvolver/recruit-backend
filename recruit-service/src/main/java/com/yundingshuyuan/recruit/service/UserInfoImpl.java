package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.yundingshuyuan.constant.RegexConstant;
import com.yundingshuyuan.recruit.api.AcademyService;
import com.yundingshuyuan.recruit.api.UserInfoService;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;
import com.yundingshuyuan.recruit.service.exception.AdminException;
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

import java.security.InvalidParameterException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userMapper;

    @Autowired
    private Converter converter;

    @Autowired
    private QrCodeUtils qrCodeUtils;
    @Autowired
    private AcademyService academyService;

    @Override
    @Transactional
    public UserInfoVo showUserInfo(String cloudId) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getCloudId, cloudId);
        //根据id查询用户信息，但此时用户信息不包含学校和书院，只有书院id
        UserInfo userInfo = userMapper.selectOne(queryWrapper);
        //根据id查询书院信息
        Academy academy = academyService.getById(userInfo.getAcademyId());
        //user_info中不包含学校和书院的名称，需要在user_infoVO中添加school和academy
        //先转型，再赋值
        UserInfoVo userInfoVO = converter.convert(userInfo, UserInfoVo.class);
        userInfoVO.setAcademy(academy.getAcademy());
        userInfoVO.setSchool(academy.getSchool());
        return userInfoVO;
    }


    @Override
    public boolean updateUserInfo(UserInfoVo userInfoVO, Integer isAdmin) {
        UserInfo userInfo = converter.convert(userInfoVO, UserInfo.class);
        /*commonValidation(userInfo);*/
        Integer integer = academyConvert(userInfoVO.getAcademy());
        userInfo.setAcademyId(integer);
        userInfo.setIsAdmin(isAdmin);
        LambdaUpdateChainWrapper<UserInfo> updateWrapper = new LambdaUpdateChainWrapper<>(userMapper);
        boolean update = updateWrapper.eq(UserInfo::getCloudId, userInfo.getCloudId())
                .set(!userInfo.getStudentNumber().isEmpty(), UserInfo::getStudentNumber, userInfo.getStudentNumber())
                .set(userInfo.getAcademyId() != null, UserInfo::getAcademyId, userInfo.getAcademyId())
                .set(!userInfo.getDirection().isEmpty(), UserInfo::getDirection, userInfo.getDirection())
                .set(userInfo.getIsAdmin() != null, UserInfo::getIsAdmin, userInfo.getIsAdmin())
                .set(!userInfo.getGender().isEmpty(), UserInfo::getGender, userInfo.getGender())
                .set(!userInfo.getEmail().isEmpty(), UserInfo::getEmail, userInfo.getEmail())
                .set(!userInfo.getMajor().isEmpty(), UserInfo::getMajor, userInfo.getMajor())
                .set(!userInfo.getPhone().isEmpty(), UserInfo::getPhone, userInfo.getPhone())
                .set(!userInfo.getName().isEmpty(), UserInfo::getName, userInfo.getName())
                .set(!userInfo.getQq().isEmpty(), UserInfo::getQq, userInfo.getQq())
                .update();
        log.info("学号{}",userInfo.getStudentNumber());
        return update;
    }

    @Override
    public boolean saveUserInfo(UserInfoVo userInfoVO) {
        UserInfo userInfo = converter.convert(userInfoVO, UserInfo.class);
        // 这啥？
        userInfo.setAcademyId(academyConvert(userInfoVO.getAcademy()));
        // 校验抛异常提前
        commonValidation(userInfo);
        // 分发权限
        String studentNumber = userInfo.getStudentNumber();
        /* 学号分类注册逻辑 */
        if (studentNumber.matches(RegexConstant.ADMIN_SID)) {
            userInfo.setIsAdmin(1);
        } else if (studentNumber.matches(RegexConstant.USER_SID)) {
            userInfo.setIsAdmin(0);
        }
        /* 信息更新逻辑 */
        if(isUserExist(userInfo)){
            log.info("User{} StdId {} 修改信息为{}}",userInfo.getName(),
                    userInfo.getStudentNumber(), userInfo.getIsAdmin() == 1 ? "管理员" : "用 户");
            // 修改
            updateUserInfo(userInfoVO, userInfo.getIsAdmin());
        } else {
            log.info("User{} StdId {} 注册信息为{}}",userInfo.getName(),
                    userInfo.getStudentNumber(), userInfo.getIsAdmin() == 1 ? "管理员" : "用 户");
            // 注册
            userMapper.insert(userInfo);
        }
        return true;
    }

    public boolean isUserExist(UserInfo userInfo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        UserInfo userInfo1 = userMapper.selectOne(queryWrapper.eq(UserInfo::getCloudId, userInfo.getCloudId()));
        if (userInfo1 == null) {
            return false;
        }
        return true;
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
    private void commonValidation(UserInfo info) throws AdminException, InvalidParameterException {
        new AbstractUserInfoValidation.Builder().add(new EmailValidation(info.getEmail()))
                .add(new PhoneValidation(info.getPhone()))
                .add(new StudentNumberValidation(info.getStudentNumber())).build().validate();
    }
}

