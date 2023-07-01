package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.friendsMapper;
import com.starQeem.qeemblog.pojo.friends;
import com.starQeem.qeemblog.service.friendsService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.Resource;
import java.util.List;

import static com.starQeem.qeemblog.util.constant.PAGE_NUM;
import static com.starQeem.qeemblog.util.constant.PAGE_SIZE;

/**
 * @Date: 2023/6/18 20:09
 * @author: Qeem
 */
@Service
public class friendsServiceImpl extends ServiceImpl<friendsMapper, friends> implements friendsService {
    @Resource
    private friendsService friendsService;
    /**
     * 获取好友列表
     *
     * @param pageNum 页面num
     * @return {@link PageInfo}<{@link friends}>
     */
    @Override
    public PageInfo<friends> getFriendsList(Integer pageNum) {
        if (pageNum == null){
            pageNum = PAGE_NUM;
        }
        PageHelper.startPage(pageNum,PAGE_SIZE);
        PageHelper.orderBy("create_time");
        List<friends> friendsList = friendsService.list();
        return new PageInfo<>(friendsList);
    }
}
