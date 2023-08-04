package com.yundingshuyuan.recruit.service;

import cn.dev33.satoken.stp.StpUtil;
import com.yundingshuyuan.recruit.api.LoginService;
import com.yundingshuyuan.recruit.dao.InterviewerMapper;
import com.yundingshuyuan.recruit.domain.InterviewerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private InterviewerMapper interMapper;
    private StpUtil stpUtil;
    @Value("${manager.id}")
    private Long superId;

    @Value("${manager.username}")
    private String superUsername;
    @Value("${manager.password}")
    private String superPassword;


    @Override
    public void login(String username, String password) {
        if (username.equals(superUsername) && password.equals(superPassword)) {
            //通过id进行登录
            stpUtil.login(superId);
            //配置用户权限信息和身份信息
            List<String> permissionList = StpUtil.getPermissionList(superId);
            List<String> roleList = StpUtil.getRoleList(superId);
            //加载用户权限信息和身份信息
            StpUtil.getSession().set("authList", permissionList);
            StpUtil.getSession().set("roleList", roleList);
            log.info("管理员登录成功{}", StpUtil.getTokenValue());
            return;
        } else {
            List<InterviewerInfo> interviewerInfos = interMapper.selectList();
            for (InterviewerInfo interviewerInfo : interviewerInfos) {
                if (username.equals(interviewerInfo.getUsername()) && password.equals(interviewerInfo.getPassword())) {
                    stpUtil.login(interviewerInfo.getGroupId());
                    log.info(stpUtil.getTokenValue());
                    List<String> permissionList = StpUtil.getPermissionList();
                    List<String> roleList = StpUtil.getRoleList();
                    //加载用户权限信息和身份信息
                    StpUtil.getSession().set("authList", permissionList);
                    StpUtil.getSession().set("roleList", roleList);
                    log.info("普通管理员登录成功");
                    return;
                }
            }
        }
    }

    public Integer getCurrentId() {
        return Integer.valueOf(stpUtil.getLoginId().toString());
    }

    @Override
    public Boolean isLogin() {
        return stpUtil.isLogin();


    }


}
