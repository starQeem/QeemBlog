package com.starQeem.qeemblog.controller;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Blog;
import com.starQeem.qeemblog.pojo.Tag;
import com.starQeem.qeemblog.pojo.Type;
import com.starQeem.qeemblog.service.BlogService;
import com.starQeem.qeemblog.service.CommentService;
import com.starQeem.qeemblog.service.TagService;
import com.starQeem.qeemblog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2023/4/27 9:22
 * @author: Qeem
 */
@Controller
public class tagsController {
    @Resource
    private TagService tagService;
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    private final static int PAGE_SIZE = 8;
    //标签分页查询
    @GetMapping(value = {"/tags/{tagId}","/tags" })
    public String getTypePage(@PathVariable(value = "tagId", required = false)Long tagId,
                              @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, Model model) {
        //获得所有标签列表
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("tagList",tagList);
        if (tagId == null){
            tagId = tagList.get(0).getId();
        }
        PageInfo<Blog> pageInfo = blogService.pageTagList(tagId, pageNum, PAGE_SIZE);
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        model.addAttribute("page",pageInfo);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        //目前所在的标签添加到model中，使页面可以获取
        model.addAttribute("currTag",tagId);
        return "tags";
    }
}
