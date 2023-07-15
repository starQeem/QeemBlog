package com.starQeem.qeemblog.service.Impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.TypeMapper;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.TypeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.starQeem.qeemblog.util.constant.*;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
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
        String getType = stringRedisTemplate.opsForValue().get(TYPE_KEY);
        if (StrUtil.isNotBlank(getType)){
            return JSONUtil.toList(getType, Type.class);
        }
        List<Type> typeList = getBaseMapper().getTypeList();
        stringRedisTemplate.opsForValue().set(TYPE_KEY, JSONUtil.toJsonStr(typeList),TYPE_TTL, TimeUnit.SECONDS);
        return typeList;
    }
    //查询6条分类信息
    @Override
    public List<Type> getIndexTpeList() {
        return getBaseMapper().getSixTypeList();
    }

    @Override
    public Type getName(String name) {
        return getBaseMapper().selectOne(Wrappers.<Type>lambdaQuery().eq(Type::getName,name));
    }

    @Override
    public void saveType(Type type) {
        String getType = stringRedisTemplate.opsForValue().get(TYPE_KEY);
        if (StrUtil.isNotBlank(getType)){
            stringRedisTemplate.delete(TYPE_KEY);
        }
        typeService.save(type);
    }

    @Override
    public void updateTypeById(Type type) {
        String getType = stringRedisTemplate.opsForValue().get(TYPE_KEY);
        if (StrUtil.isNotBlank(getType)){
            stringRedisTemplate.delete(TYPE_KEY);
        }
        typeService.updateById(type);
    }

    @Override
    public boolean removeTypeById(Long id) {
        String getType = stringRedisTemplate.opsForValue().get(TYPE_KEY);
        if (StrUtil.isNotBlank(getType)){
            stringRedisTemplate.delete(TYPE_KEY);
        }
        return typeService.removeById(id);
    }
}
