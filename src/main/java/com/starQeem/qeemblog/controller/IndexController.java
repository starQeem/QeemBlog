package com.starQeem.qeemblog.controller;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Tag;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.Resource;
import java.util.List;

import static com.starQeem.qeemblog.util.constant.PAGE_SIZE;


@Controller
public class IndexController {
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;
    @Resource
    private MessageService messageService;

    @GetMapping(value = {"/", "/{pageNum}"})
    public String index(@PathVariable(value = "pageNum", required = false) Integer pageNum, Model model,String title) {
        PageInfo<Blog> pageInfo = blogService.searchList(pageNum, PAGE_SIZE,title);
        List<Type> typeList = typeService.getIndexTpeList();
        List<Tag> tagList = tagService.getIndexTagList();
        List<Blog> newBlogList = blogService.newBlogList();
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        Integer messageCount = messageService.getMessageCount();
        model.addAttribute("page", pageInfo);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("messageCount", messageCount);
        model.addAttribute("typeList", typeList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("newBlogList", newBlogList);
        return "/index";
    }
    //跳转到关于我页面
    @GetMapping("/about")
    public String about(Model model){
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        Integer messageCount = messageService.getMessageCount();
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("messageCount", messageCount);
        return "about";
    }
}
