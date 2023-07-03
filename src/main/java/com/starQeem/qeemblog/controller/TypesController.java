package com.starQeem.qeemblog.controller;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.pojo.friends;
import com.starQeem.qeemblog.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

import static com.starQeem.qeemblog.util.constant.PAGE_SIZE;

@Controller
public class TypesController {
    @Resource
    private TypeService typeService;
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    @Resource
    private MessageService messageService;
    @Resource
    private friendsService friendsService;
    //分类分页查询
    @GetMapping(value = {"/types/{typeId}","/types" })
    public String getTypePage(@PathVariable(value = "typeId", required = false)Long typeId,
                              @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, Model model) {
        //获得所有分类列表
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);
        if (typeId == null){
            typeId = typeList.get(0).getId();
        }
        PageInfo<Blog> pageInfo = blogService.pageTypeList(typeId, pageNum, PAGE_SIZE);
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        Integer messageCount = messageService.getMessageCount();
        List<friends> friendsList = friendsService.getRecommendFriends();
        List<Blog> newBlogList = blogService.newBlogList();
        model.addAttribute("page",pageInfo);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("messageCount", messageCount);
        model.addAttribute("friendsList", friendsList);
        model.addAttribute("newBlogList", newBlogList);
        //目前所在的分类添加到model中，使页面可以获取
        model.addAttribute("currType",typeId);
        return "types";
    }
}
