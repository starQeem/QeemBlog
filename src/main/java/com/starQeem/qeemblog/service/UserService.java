package com.starQeem.qeemblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starQeem.qeemblog.pojo.User;

public interface UserService extends IService<User> {
    User checkUser(String username , String password);
}
