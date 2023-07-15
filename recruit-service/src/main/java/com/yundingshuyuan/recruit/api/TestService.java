package com.yundingshuyuan.recruit.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yundingshuyuan.recruit.domain.User;

public interface TestService {
    Page<User> test();
}
