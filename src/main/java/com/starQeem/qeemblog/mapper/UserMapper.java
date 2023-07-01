package com.starQeem.qeemblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starQeem.qeemblog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
