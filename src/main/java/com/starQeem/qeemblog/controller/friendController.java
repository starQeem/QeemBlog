package com.starQeem.qeemblog.controller;

import com.starQeem.qeemblog.pojo.friends;
import com.starQeem.qeemblog.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2023/6/18 21:02
 * @author: Qeem
 */
@Controller
public class friendController {
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    @Resource
    private MessageService messageService;
    @Resource
    private friendsService friendsService;
    @GetMapping("/friends")
    public String friends(Model model){
        List<friends> friendsList = friendsService.list();
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        Integer messageCount = messageService.getMessageCount();
        model.addAttribute("friendsList",friendsList);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("messageCount", messageCount);
        return "friends";
    }
}
