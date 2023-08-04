package com.yundingshuyuan.recruit.web.config;


import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserInfoFilter implements Filter {

    //路径匹配工具类
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // 获取请求的路径
        String requestURI = httpRequest.getRequestURI();

        String[] urls = new String[]{
                "/v3/**", "/doc.html", "/login", "/actuator/**", "/display/**"
        };

        if (check(urls, requestURI)) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

    }

    public Boolean check(String[] urls, String url) {
        for (String s : urls) {
            boolean match = PATH_MATCHER.match(s, url);
            if (match == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
