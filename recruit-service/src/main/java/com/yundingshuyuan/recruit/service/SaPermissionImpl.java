package com.yundingshuyuan.recruit.service;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yundingshuyuan.recruit.dao.InterviewerMapper;
import com.yundingshuyuan.recruit.domain.InterviewerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SaPermissionImpl implements StpInterface {
    @Autowired
    private InterviewerMapper interMapper;
    @Value("${manager.id}")
    private Integer superId;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        //根据id进行判断用户和管理员
        Integer id = Integer.valueOf(o.toString());
        List<String> list = new ArrayList<>();
        //管理员有全部权限
        if (id == superId) {
            list.add("user.*");
        }
        //定义用户权限
        LambdaQueryWrapper<InterviewerInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(InterviewerInfo::getGroupId, id);
        InterviewerInfo interviewer = interMapper.selectOne(queryWrapper);
        if (interviewer != null) {
            list.add("user:add");
        }
        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        //根据id进行判断用户和管理员
        Integer id = Integer.valueOf(o.toString());

        List<String> list = new ArrayList<>();
        //管理员身份
        if (id == superId) {
            list.add("super-admin");
        }
        long infoId = id.longValue();
        //定义用户权限
        LambdaQueryWrapper<InterviewerInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(InterviewerInfo::getGroupId, id);
        InterviewerInfo interviewer = interMapper.selectOne(queryWrapper);
        if (interviewer != null) {
            list.add("user:admin");
        }
        return list;
    }
}
