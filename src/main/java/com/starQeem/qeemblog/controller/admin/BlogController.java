package com.starQeem.qeemblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.mapper.TagMapper;
import com.starQeem.qeemblog.mapper.TypeMapper;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Tag;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.annotation.Resource;
import java.util.List;

import static com.starQeem.qeemblog.util.constant.PAGE_SIZE;
import static com.starQeem.qeemblog.util.constant.ZERO;


@Controller
@RequestMapping("/admin")
public class BlogController {

    @Resource
    private BlogService blogService;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private TagMapper tagMapper;
    /*
     * 博客分页查询
     * */
    @GetMapping(value = {"/blogs/{pageNum}", "/blogs"})
    public String BlogListPage(@PathVariable(value = "pageNum", required = false) Integer pageNum, Model model) {
        PageInfo<Blog> pageInfo = blogService.pageBlogList(pageNum, PAGE_SIZE);
        model.addAttribute("page", pageInfo);
        return "admin/blogs";
    }
    /*
     * 跳转到博客新增页面
     * */
    @GetMapping("/blogs/input")
    public String BlogsInput(Model model) {
        List<Type> typeList = typeMapper.selectList(null);
        List<Tag> tags = tagMapper.selectList(null);
        model.addAttribute("typeList", typeList);
        model.addAttribute("tags",tags);
        return "admin/blogs-input";
    }

    /*
     * 博客发布
     * */
    @PostMapping("/blogs")
    public String insertBlogs(Blog blog, RedirectAttributes attributes) {
        if (blog != null) {
            Integer i = blogService.saveBlog(blog);
            if (i > ZERO) {
                attributes.addFlashAttribute("message", "发布成功");
            } else {
                attributes.addFlashAttribute("message", "发布失败");
            }
        } else {
            attributes.addFlashAttribute("message", "没有传入博客!");
        }
        return "redirect:/admin/blogs";
    }
    /*
    *跳转到博客编辑页面
    * */
    @GetMapping("/blogs/{id}/edit")
    public String editBlogs(@PathVariable("id") Long blogId,Model model){
        List<Type> typeList = typeMapper.selectList(null);
        List<Tag> tags = tagMapper.selectList(null);
        Blog blog = blogService.getBlog(blogId);
        model.addAttribute("typeList",typeList);
        model.addAttribute("blog",blog);
        model.addAttribute("tags",tags);
        return "admin/blogs-edit";
    }
    /*
    * 博客编辑
    * */
    @PutMapping("/blogs")
    public String updateBlogs(Blog blog,RedirectAttributes attributes){
        Integer i = blogService.updateBlog(blog);
        if (i > ZERO){
            attributes.addFlashAttribute("message","修改成功!");
        }else {
            attributes.addFlashAttribute("message","修改失败!");
        }
        return "redirect:/admin/blogs";
    }
    /*
    * 删除博客
    * */
    @RequestMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable("id") Long id,RedirectAttributes attributes){
        boolean remove = blogService.removeBlogById(id);
        if (remove){
            attributes.addFlashAttribute("message","删除成功!");
        }else {
            attributes.addFlashAttribute("message","删除失败!");
        }
        return "redirect:/admin/blogs";
    }
}
