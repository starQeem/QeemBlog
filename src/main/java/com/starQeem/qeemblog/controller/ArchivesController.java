package com.starQeem.qeemblog.controller;

import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.service.BlogService;
import com.starQeem.qeemblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ArchivesController {
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    //归档
    @GetMapping("/archives")
    public String archives(Model model){
        List<Blog> list = blogService.getArchivesList();
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        model.addAttribute("memoryList",list);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        return "archives";
    }
}
