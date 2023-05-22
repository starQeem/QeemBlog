package com.starQeem.qeemblog.controller;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.service.BlogService;
import com.starQeem.qeemblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;


@Controller
public class IndexController {
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;

    @GetMapping(value = {"/", "/{pageNum}"})
    public String index(@PathVariable(value = "pageNum", required = false) Integer pageNum, Model model,String title) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (title == null){
            title = "";
        }
        PageInfo<Blog> pageInfo = blogService.searchList(pageNum, 8,title);
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        model.addAttribute("page", pageInfo);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        return "/index";
    }
    //跳转到关于我页面
    @GetMapping("/about")
    public String about(Model model){
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        return "about";
    }
}
