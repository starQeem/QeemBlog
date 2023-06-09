package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.TypeMapper;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.starQeem.qeemblog.util.constant.PAGE_NUM;
import static com.starQeem.qeemblog.util.constant.PAGE_SIZE;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Resource
    private TypeService typeService;
    //分页查询
    @Override
    public PageInfo<Type> pageTypeList(Integer page, Integer pageSize) {
        if (page==null){
            page = PAGE_NUM;
        }
        PageHelper.startPage(page,pageSize);
        PageHelper.orderBy("id desc");
        List<Type> typeList = getBaseMapper().selectList(null);
        return new PageInfo<>(typeList, PAGE_SIZE);
    }

    @Override
    public List<Type> getTypeList() {
        return getBaseMapper().getTypeList();
    }
    //查询6条分类信息
    @Override
    public List<Type> getIndexTpeList() {
        return getBaseMapper().getSixTypeList();
    }
}
