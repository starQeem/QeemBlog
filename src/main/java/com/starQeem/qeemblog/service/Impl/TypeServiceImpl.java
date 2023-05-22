package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.TypeMapper;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    //分页查询
    @Override
    public PageInfo<Type> pageTypeList(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        PageHelper.orderBy("id desc");
        List<Type> typeList = getBaseMapper().selectList(null);
        return new PageInfo<>(typeList, 5);
    }

    @Override
    public List<Type> getTypeList() {
        return getBaseMapper().getTypeList();
    }
}
