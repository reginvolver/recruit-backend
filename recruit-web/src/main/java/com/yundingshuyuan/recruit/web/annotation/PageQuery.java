package com.yundingshuyuan.recruit.web.annotation;

import java.lang.annotation.*;

/**
 * <a href="https://blog.csdn.net/qq_37128049/article/details/105045717">参考 @author: an yu</a>
 *
 * @description: PageSize注解类
 * 加在方法上或者类上开启结果分页
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PageQuery {
    String pageSize() default "size";

    String currentPage() default "current";
}

