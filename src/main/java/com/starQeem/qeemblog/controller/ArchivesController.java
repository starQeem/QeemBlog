package com.starQeem.qeemblog.controller;

import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.friends;
import com.starQeem.qeemblog.service.BlogService;
import com.starQeem.qeemblog.service.CommentService;
import com.starQeem.qeemblog.service.MessageService;
import com.starQeem.qeemblog.service.friendsService;
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
    @Resource
    private MessageService messageService;
    @Resource
    private friendsService friendsService;
    //归档
    @GetMapping("/archives")
    public String archives(Model model){
        List<Blog> list = blogService.getArchivesList();
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        Integer messageCount = messageService.getMessageCount();
        List<friends> friendsList = friendsService.getRecommendFriends();
        List<Blog> newBlogList = blogService.newBlogList();
        model.addAttribute("memoryList",list);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("messageCount", messageCount);
        model.addAttribute("friendsList", friendsList);
        model.addAttribute("newBlogList", newBlogList);
        return "archives";
    }
}
