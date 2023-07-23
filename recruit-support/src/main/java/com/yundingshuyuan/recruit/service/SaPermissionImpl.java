package com.yundingshuyuan.recruit.service;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Component
public class SaPermissionImpl implements StpInterface {
    @Value("${manager.id}")
    private Long superId;
    @Value("${user.id}")
    private Long userId;
    @Override
    public List<String> getPermissionList(Object o, String s) {
        //根据id进行判断用户和管理员
        Long id = Long.valueOf(o.toString());
        List<String> list = new ArrayList<>();
        //管理员有全部权限
        if(id == superId){
            list.add("user.*");
            }
        //定义用户权限
        if(id == userId){
            list.add("user.add");
        }
        return list;
     }

    @Override
    public List<String> getRoleList(Object o, String s) {
        //根据id进行判断用户和管理员
        Long id = Long.valueOf(o.toString());
        List<String> list = new ArrayList<>();
        //管理员身份
        if(id == superId){
            list.add("super-admin");
        }
        //用户身份
        if(id == userId){
            list.add("admin");
        }
        return list;
    }
}
