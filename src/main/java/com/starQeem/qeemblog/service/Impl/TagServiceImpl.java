package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.TagMapper;
import com.starQeem.qeemblog.pojo.Tag;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.starQeem.qeemblog.util.constant.PAGE_NUM;
import static com.starQeem.qeemblog.util.constant.PAGE_SIZE;

/**
 * @Date: 2023/4/26 12:59
 * @author: Qeem
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Resource
    private TagService tagService;
    @Resource
    private TagMapper tagMapper;
    /*
    * 标签列表分页查询
    * */
    @Override
    public PageInfo<Tag> pageTagList(Integer page, int pageSize) {
        if (page==null){
            page = PAGE_NUM;
        }
        PageHelper.startPage(page,pageSize);
        PageHelper.orderBy("id desc");
        List<Tag> tagList = tagMapper.selectList(null);
        return new PageInfo<>(tagList,PAGE_SIZE);
    }
    /*
    * 标签编辑
    * */
    @Override
    public boolean updateTagById(Tag tag) {
        Tag tagByName = tagMapper.selectOne(Wrappers.<Tag>lambdaQuery().eq(Tag::getName,tag.getName()));
        if (tagByName != null){
            return false;
        }else {
            return tagService.updateById(tag);
        }
    }
    /*
    * 添加标签
    * */
    @Override
    public boolean saveTag(Tag tag) {
        Tag tagByName = tagMapper.selectOne(Wrappers.<Tag>lambdaQuery().eq(Tag::getName,tag.getName()));
        if (tagByName != null){
            return false;
        }else {
            return tagService.save(tag);
        }
    }
    /*
    * 根据ids查询标签
    * */
    @Override
    public List<Tag> getTagByIds(String tagIds) {
        Long[] idArray = Arrays.stream(tagIds.split(",")).map(Long::parseLong).toArray(Long[]::new);
        List<Tag> tagList = new ArrayList<>();
        for (int i = 0; i < idArray.length; i++) {
            Tag tagById = tagService.getById(idArray[i]);
            if (tagById != null){
                tagList.add(tagById);
            }
        }
        return tagList;
    }
    /*
    * 获取所有标签列表
    * */
    @Override
    public List<Tag> getTagList() {
        return tagMapper.getTagList();
    }

    @Override
    public List<Tag> getIndexTagList() {
        return tagMapper.getTenTagList();
    }
}
