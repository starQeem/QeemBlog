package com.starQeem.qeemblog.controller;

import com.starQeem.qeemblog.pojo.Comment;
import com.starQeem.qeemblog.pojo.User;
import com.starQeem.qeemblog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class CommentController {

    @Resource
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar="https://pic.imgdb.cn/item/64171e7fa682492fcc478f1f.jpg";

    //查询评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> rootCommentList = commentService.getRootCommentList(blogId);//获取所有根评论，并且注入回复列表
        model.addAttribute("commentList", rootCommentList);
        return "blog :: commentList";
    }

    //新增评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) throws MessagingException {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            //设置头像
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();

    }

    //删除评论
    @GetMapping("/comments/{id}/delete")
    public String delete(@PathVariable("id")Long commentId, @RequestParam("blogId")Long blogId,HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user != null){
            commentService.removeById(commentId);
        }
        return "redirect:/blogs/"+blogId;
    }

}
