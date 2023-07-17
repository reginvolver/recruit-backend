package com.yundingshuyuan.recruit.config;

import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 1、Sa-Token 整合 jwt (Simple 简单模式)
 * 2、Sa-Token 拦截器
 * 3、开启注解鉴权
 *
 * @Author cr
 * @Date 2023/7/17 18:47
 * 注入jwt实现
 */
@Configuration
@RestControllerAdvice   //异常拦截器
public class SaTokenConfiguration implements WebMvcConfigurer {

    // Sa-Token 整合 jwt (Simple 简单模式)
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    //  =============================  拦截器  ===============================
    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public SaResult handlerException(NotLoginException e) {

        // 打印堆栈，以供调试
        e.printStackTrace();
        // 返回给前端
        return SaResult.error(e.getMessage());
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public SaResult handlerException(NotPermissionException e) {
        e.printStackTrace();
        return SaResult.error("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public SaResult handlerException(NotRoleException e) {
        e.printStackTrace();
        return SaResult.error("缺少角色：" + e.getRole());
    }

    // 拦截：二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public SaResult handlerException(NotSafeException e) {
        e.printStackTrace();
        return SaResult.error("二级认证校验失败：" + e.getService());
    }


    // 拦截：Http Basic 校验失败异常
    @ExceptionHandler(NotBasicAuthException.class)
    public SaResult handlerException(NotBasicAuthException e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

    // 拦截：其它所有异常
    @ExceptionHandler(Exception.class)
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }


    //  =============================  开启注解鉴权  ===============================

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能，然后去Controller层加上对应的注解就可以了
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");

    //@SaCheckLogin: 登录校验 —— 只有登录之后才能进入该方法。
    //@SaCheckRole("admin"): 角色校验 —— 必须具有指定角色标识才能进入该方法。
    //@SaCheckPermission("user:add"): 权限校验 —— 必须具有指定权限才能进入该方法。
    //@SaCheckSafe: 二级认证校验 —— 必须二级认证之后才能进入该方法。
    //@SaCheckBasic: HttpBasic校验 —— 只有通过 Basic 认证后才能进入该方法。
    //@SaIgnore：忽略校验 —— 表示被修饰的方法或类无需进行注解鉴权和路由拦截器鉴权。
    //@SaCheckDisable("comment")：账号服务封禁校验 —— 校验当前账号指定服务是否被封禁。
    //@SaCheckOr：批量鉴权，具体使用去文档里看
    }



}
