package com.starQeem.qeemblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Blog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogService extends IService<Blog> {
    //分页查询
    PageInfo<Blog> pageBlogList(Integer pageNum, Integer pageSize);
    //获取博客列表的信息
    List<Blog> getBlogsPage();
    //新增
    Integer saveBlog(Blog blog);
    //修改
    Integer updateBlog(Blog blog);
    //获取博客内容
    Blog getBlog(Long blogId);
    //当前分类分页
    PageInfo<Blog> pageTypeList(Long typeId, Integer pageNum, Integer pageSize);
    //归档
    List<Blog> getArchivesList();
    //首页搜索
    PageInfo<Blog> searchList(Integer pageNum, Integer pageSize, String title);
    //博客详情
    Blog getBlogDetail(Long id);
    //标签查询
    PageInfo<Blog> pageTagList(Long tagId, Integer pageNum, int pageSize);
    //查询博客文章总数
    Integer getBlogCount();
    //查询博客浏览次数总数
    Integer getViewCount();
}
