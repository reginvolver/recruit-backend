package com.yundingshuyuan.recruit.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yundingshuyuan.recruit.api.TestService;
import com.yundingshuyuan.recruit.domain.User;
import com.yundingshuyuan.recruit.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<User> test() {
        Page<User> userPage = userMapper.selectPage(new Page<>(1, 10),
                new LambdaQueryWrapper<>()
        );
        return userPage;
    }
}
