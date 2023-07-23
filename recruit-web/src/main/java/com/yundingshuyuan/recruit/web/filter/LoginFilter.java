package com.yundingshuyuan.recruit.web.filter;


import com.alibaba.fastjson.JSON;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter" ,value = "/*")
@Slf4j
public class LoginFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        String URI = request.getRequestURI();
        //定义不再需要处理的请求路径
        String[] urls = new String[]{
                "/login","/logout","/actuator/**"
        };
        if(check(urls,URI)){
            filterChain.doFilter(request,response);
            return;
        }
        log.info("拦截到请求：{}",URI);
        Cookie[] cookies = request.getCookies();
        try {
            if (cookies.length != 0) {
                log.info("用户已登录");
                filterChain.doFilter(request, response);
                return;
            }
        }catch (NullPointerException e){
            log.info("用户未登录");
            response.getWriter().write(JSON.toJSONString((BasicResultVO.fail("用户未登录"))));
            return ;
        }
    }


    public Boolean check(String[] urls,String url){
        for (String s : urls) {
            boolean match = PATH_MATCHER.match(s,url);
            if(match == true){
                return true;
            }
        }
        return false;
    }


}
