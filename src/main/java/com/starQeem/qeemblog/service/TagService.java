package com.starQeem.qeemblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Tag;
import com.starQeem.qeemblog.pojo.Type;

import java.util.List;

/**
 * @Date: 2023/4/26 12:59
 * @author: Qeem
 */
public interface TagService extends IService<Tag> {
    PageInfo<Tag> pageTagList(Integer page, int pageSize);
    boolean updateTagById(Tag tag);
    boolean saveTag(Tag tag);
    List<Tag> getTagByIds(String tagIds);
    List<Tag> getTagList();
    List<Tag> getIndexTagList();
    boolean removeTagById(Long id);
}
