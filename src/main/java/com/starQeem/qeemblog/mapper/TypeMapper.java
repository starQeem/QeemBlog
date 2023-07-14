package com.starQeem.qeemblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starQeem.qeemblog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    //获取所有类型
    List<Type> getTypeList();
    List<Type> getSixTypeList();
}
