package com.starQeem.qeemblog.controller;

import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.pojo.Message;
import com.starQeem.qeemblog.pojo.User;
import com.starQeem.qeemblog.pojo.friends;
import com.starQeem.qeemblog.service.BlogService;
import com.starQeem.qeemblog.service.CommentService;
import com.starQeem.qeemblog.service.MessageService;
import com.starQeem.qeemblog.service.friendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Date: 2023/6/17 17:53
 * @author: Qeem
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    @Resource
    private friendsService friendsService;
    @Value("${comment.avatar}")
    private String avatar;

    //显示留言板页面
    @GetMapping(value = {"/message/{pageNum}","/message"})
    public String message(Model model,@PathVariable(value = "pageNum",required = false)Integer pageNum){//从首页点进页面需要加载page信息，否则页面会报空指针
        PageInfo<Message> pageInfo = messageService.getMessageList(pageNum);
        Integer blogCount = blogService.getBlogCount();
        Integer viewCount = blogService.getViewCount();
        Integer commentCount = commentService.getCommentCount();
        Integer messageCount = messageService.getMessageCount();
        List<friends> friendsList = friendsService.getRecommendFriends();
        model.addAttribute("page",pageInfo);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("messageCount", messageCount);
        model.addAttribute("friendsList", friendsList);
        return "leave";
    }

    //查询评论列表
    @GetMapping(value = {"/messagecomment","/messagecomment/{pageNum}"})
    public String messageComment(@PathVariable(value = "pageNum",required = false) Integer pageNum, Model model) {
        if (pageNum == null){
            pageNum = 1;
        }
        PageInfo<Message> pageInfo = messageService.getMessageList(pageNum);
        model.addAttribute("page",pageInfo);

        return "leave :: messageList";
    }

    //新增评论
    @PostMapping("/message")
    public String message(Message message, HttpSession session, Integer pageNum,
                          HttpServletRequest request,
                          HttpServletResponse response) throws MessagingException {
        User user = (User) session.getAttribute("user");
        if (user != null) {//如果是管理员，设置管理员的权限
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(true);
        } else {//如果不是管理员
            //设置头像
            message.setAvatar(avatar);
        }
        int i = messageService.saveMessage(message);//将评论信息保存数据库

        messageService.sendMail(user,message);//发送评论提醒邮件


        return "redirect:/messagecomment/"+pageNum;


    }

    //删除评论
    @GetMapping("/message/{id}/delete")
    public String delete(@PathVariable("id")Long id,HttpSession session,Integer pageNum){
        User user = (User) session.getAttribute("user");
        if(user != null){
            boolean b = messageService.removeById(id);
        }
        return "redirect:/message/"+pageNum;

    }
}
