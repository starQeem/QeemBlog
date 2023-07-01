package com.starQeem.qeemblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Type;

import java.util.List;

public interface TypeService extends IService<Type> {
    //分页查询
    PageInfo<Type> pageTypeList(Integer page,Integer pageSize);
    //获取所有类型
    List<Type> getTypeList();
    //获取6条分类信息
    List<Type> getIndexTpeList();
}
