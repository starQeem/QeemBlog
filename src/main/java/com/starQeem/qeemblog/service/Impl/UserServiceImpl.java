package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starQeem.qeemblog.mapper.UserMapper;
import com.starQeem.qeemblog.pojo.User;
import com.starQeem.qeemblog.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserService userService;
    @Override
    public User checkUser(String username, String password) {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username).eq("password",md5DigestAsHex);
        return userService.getBaseMapper().selectOne(queryWrapper);

    }
}
