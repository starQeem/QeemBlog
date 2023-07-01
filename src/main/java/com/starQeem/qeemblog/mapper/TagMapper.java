package com.starQeem.qeemblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starQeem.qeemblog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Date: 2023/4/26 12:58
 * @author: Qeem
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> getTagList();
    List<Tag> getTenTagList();
}
