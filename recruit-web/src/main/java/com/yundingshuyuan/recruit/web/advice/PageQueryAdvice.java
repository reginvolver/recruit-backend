package com.yundingshuyuan.recruit.web.advice;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import com.yundingshuyuan.recruit.web.annotation.PageQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Slf4j
@ControllerAdvice(basePackages = "com.yundingshuyuan.recruit.web.controller")
@Component
@Order(123)
public class PageQueryAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getContainingClass().isAnnotationPresent(PageQuery.class) || returnType.hasMethodAnnotation(PageQuery.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Map<String, Long> parameMap = getparameters(returnType);
        if (parameMap.isEmpty()) {
            return data;
        } else if (Objects.nonNull(data) && Objects.nonNull(data.getClass()) && data instanceof Collection) {
            return pageBySubList(parameMap.get("current"), parameMap.get("size"), new ArrayList<>(((Collection<?>) data)));
        }
        return data;
    }

    private Map<String, Long> getparameters(MethodParameter returnType) {
        try {
            // 获取分页参数匹配名称
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
                    .getRequest();
            String requestBody = IoUtil.read(request.getReader());
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            // 获取设定
            Map<String, Object> valueMap = AnnotationUtil.getAnnotationValueMap(returnType.getMethod(), PageQuery.class);
            String currentPageName = (String) valueMap.get("currentPage");
            String pageSizeName = (String) valueMap.get("pageSize");
            // 存储
            HashMap<String, Long> map = new HashMap<>(2);
            map.put("current", jsonObject.getLong(currentPageName));
            map.put("size", jsonObject.getLong(pageSizeName));
            return map;
        } catch (NullPointerException | IOException e) {
            return Collections.emptyMap();
        }
    }

    /**
     * 利用subList方法进行分页
     * <a href="https://blog.csdn.net/qq_37128049/article/details/105045717">链接</a>
     *
     * @param list 分页数据
     */
    private <T> List<T> pageBySubList(long current, long size, List<T> list) {
        if (size < 1 || current < 1 || list.size() / size < current - 1) {
            return new ArrayList<>();
        }
        return list.subList((int) ((current - 1) * size),
                size * current < list.size() ? (int) (size * current) : list.size());
    }

}
