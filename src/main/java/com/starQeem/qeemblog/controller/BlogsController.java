package com.starQeem.qeemblog.controller;

import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Tag;
import com.starQeem.qeemblog.pojo.friends;
import com.starQeem.qeemblog.service.*;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
public class BlogsController {
    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private CommentService commentService;
    @Resource
    private MessageService messageService;
    @Resource
    private friendsService friendsService;
    //博客详情
    @GetMapping("/blogs/{id}")
    public String blogs(Model model,@PathVariable("id") Long id){
        Blog blog = blogService.getBlogDetail(id);
        String tagIds = blog.getTagIds();
        List<Tag> tagList = tagService.getTagByIds(tagIds);
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        Integer messageCount = messageService.getMessageCount();
        List<friends> friendsList = friendsService.getRecommendFriends();
        List<Blog> newBlogList = blogService.newBlogList();
        model.addAttribute("blog",blog);
        model.addAttribute("tagList",tagList);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("messageCount", messageCount);
        model.addAttribute("friendsList", friendsList);
        model.addAttribute("newBlogList", newBlogList);
        return "blog";
    }
}
