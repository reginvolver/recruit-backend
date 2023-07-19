package com.yundingshuyuan.recruit.utils;

import cn.dev33.satoken.stp.StpUtil;

import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/17 18:11
 */
public class SaTokenUtils {

//=======================  登录认证  =========================

    /**
     * 根据id登录，并判断是否登陆成功
     *
     * @param id
     * @return
     */
    public void login(Object id){
        StpUtil.login(id);
    }

    /**
     * 判断当前会话是否已经登录
     *
     * @return true=已登录，false=未登录
     */
    public boolean isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 获取当前会话的 token 值
     * @return
     */
    public String getToken(){
        return StpUtil.getTokenValue();
    }

    /**
     * 根据token得到id，如果未登录，则返回 null
     *
     * @param token
     * @return
     * 这里边的id类型好像都是Object
     */
    public Object getIdByToken(String token){
         return StpUtil.getLoginIdByToken(token);
    }

    /**
     * 获取当前token还剩多久过期，单位是s，返回-1代表永久有效
     *
     * @param token
     * @return
     */
    public long getTokenTimeout(String token){
        return StpUtil.getTokenTimeout();
    }

  //========================  权限认证  ==============================

    /**
     * 获取当前账号所拥有的权限集合
     * @return
     */
    public List<String> getPermissionList(){
        return StpUtil.getPermissionList();
    }

    /**
     * 判断当前登录用户是否拥有指定的权限(单个)
     *
     * @param perm
     * @return
     */
    public boolean hasPermission(String perm) {
        return StpUtil.hasPermission(perm);
    }


    /**
     * 判断当前登录用户是否拥有指定的角色
     * @param role
     * @return
     */
    public boolean hasRole(String role) {
        return StpUtil.hasRole(role);
    }

    //========================  踢人下线、注销  ==============================

    /**
     * 将指定id的用户踢下线
     *
     * @param id
     */
    public void kickOut(Object id){
        StpUtil.kickout(id);
    }

    /**
     * 根据token踢下线
     *
     * @param token
     */
    public void kickOut(String token){
        StpUtil.kickoutByTokenValue(token);
    }

}
