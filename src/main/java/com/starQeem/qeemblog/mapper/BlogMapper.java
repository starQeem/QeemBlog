package com.starQeem.qeemblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    //查询所有数据
    List<Blog> getBlogsPage();
    //新增
    Integer insertBlog(Blog blog);
    //博客编辑回显
    Blog getBlogById(Long id);
    //博客更新
    Integer updateBlogById(Blog blog);
    //当前分类分页
    List<Blog> getBlogListByTypeId(Long typeId);
    //归档
    List<Blog> getArchivesList();
    //搜索
    List<Blog> getSearchList(String title);
    Blog getBlogDetail(Long id);
    //更新浏览次数
    void updateViews(Long id);
    //当前标签分页
    List<Blog> getBlogListByTagId(Long tagId);
    //查询博客文章浏览总数
    Integer getViewCount();
}
