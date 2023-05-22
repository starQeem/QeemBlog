package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starQeem.qeemblog.mapper.UserMapper;
import com.starQeem.qeemblog.pojo.User;
import com.starQeem.qeemblog.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User checkUser(String username, String password) {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        return getBaseMapper().selectOneByUsernameAndPassword(username, md5DigestAsHex);

    }
}
