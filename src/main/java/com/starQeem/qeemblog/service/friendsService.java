package com.starQeem.qeemblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.friends;

/**
 * @Date: 2023/6/18 20:09
 * @author: Qeem
 */
public interface friendsService extends IService<friends> {
    PageInfo<friends> getFriendsList(Integer pageNum);
}
