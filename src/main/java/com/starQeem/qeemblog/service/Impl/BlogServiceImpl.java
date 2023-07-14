package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.BlogMapper;
import com.starQeem.qeemblog.mapper.TypeMapper;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.BlogService;
import com.starQeem.qeemblog.util.MarkdownUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.starQeem.qeemblog.util.constant.*;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private BlogService blogService;
    @Resource
    private BlogMapper blogMapper;
    //分页查询
    @Override
    public PageInfo<Blog> pageBlogList(Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = PAGE_NUM;
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time desc");
        List<Blog> blogList = getBlogsPage();
        return new PageInfo<>(blogList, PAGE_SIZE);
    }

    //获取博客列表的信息
    @Override
    public List<Blog> getBlogsPage() {
        List<Blog> blogList = getBaseMapper().getBlogsPage();//获得[博客管理]页面需要的字段，type中只有id
        blogList.stream()  //为blog中的type注入name属性
                .peek(blog -> {
                    Type type = typeMapper.selectById(blog.getType().getId());
                    blog.setType(type);
                })
                .collect(Collectors.toList());

        return blogList;
    }
    //新增
    @Override
    public Integer saveBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        blog.setCreateTime(new Date());
        blog.setViews(ZERO);
        return getBaseMapper().insert(blog);
    }

    //修改
    @Override
    public Integer updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        return blogMapper.updateById(blog);
    }
    //获取博客内容
    @Override
    public Blog getBlog(Long blogId) {
        return getBaseMapper().getBlogById(blogId);
    }
    //当前分类分页
    @Override
    public PageInfo<Blog> pageTypeList(Long typeId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("update_time desc");
        List<Blog> blogList = getBaseMapper().getBlogListByTypeId(typeId);
        return new PageInfo<>(blogList);
    }
    /*
    *
    * 当前标签分页
    */
    @Override
    public PageInfo<Blog> pageTagList(Long tagId, Integer pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("update_time desc");
        List<Blog> blogList = getBaseMapper().getBlogListByTagId(tagId);
        return new PageInfo<>(blogList);
    }
    /*
    * 查询博客文章总数
    * */
    @Override
    public Integer getBlogCount() {
        return Math.toIntExact(blogService.getBaseMapper().selectCount(null));
    }
    /*
    * 查询博客浏览次数总数
    * */
    @Override
    public Integer getViewCount() {
        return blogMapper.getViewCount();
    }
    /*
    * 最新推荐
    * */
    @Override
    public List<Blog> newBlogList() {
        return getBaseMapper().selectList(Wrappers.<Blog>lambdaQuery()
                .select(Blog::getId,Blog::getTitle)
                .last("order by create_time desc limit 8"));
    }

    //归档
    @Override
    public List<Blog> getArchivesList() {
        return getBaseMapper().selectList(Wrappers.<Blog>lambdaQuery()
                .select(Blog::getId,Blog::getTitle,Blog::getUpdateTime,Blog::getCreateTime,Blog::getFirstPicture)
                .last("order by update_time desc"));
    }
    //首页搜索
    @Override
    public PageInfo<Blog> searchList(Integer pageNum, Integer pageSize, String title) {
        if (pageNum == null) {
            pageNum = PAGE_NUM;
        }
        if (title == null){
            title = "";
        }else {
            pageSize = SEARCH_PAGE_SIZE;
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time desc");
        List<Blog> blogList = getSearch(title);
        return new PageInfo<>(blogList, PAGE_SIZE);
    }
    //博客详情
    @Override
    @Transactional
    public Blog getBlogDetail(Long id) {
        Blog blog = blogMapper.selectOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, id));
        Type type = typeMapper.selectById(blog.getTypeId());//注入type
        blog.setType(type);
        String html = MarkdownUtil.markdownToHtml(blog.getContent());//Markdown语法转html
        blog.setContent(html);
        blogMapper.update(blog,Wrappers.<Blog>lambdaUpdate().eq(Blog::getId, blog.getId()).set(Blog::getViews, blog.getViews() + 1));
        return blog;
    }
    private List<Blog> getSearch(String title) {
        List<Blog> blogList = getBaseMapper().getSearchList(title);//获得[博客管理]页面需要的字段，type中只有id
        blogList
                .forEach(blog -> {
                    Type type = typeMapper.selectById(blog.getType().getId());
                    blog.setType(type);
                });

        return blogList;
    }

}
