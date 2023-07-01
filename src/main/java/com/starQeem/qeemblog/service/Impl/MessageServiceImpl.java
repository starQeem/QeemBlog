package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starQeem.qeemblog.pojo.Mail;
import com.starQeem.qeemblog.pojo.Message;
import com.starQeem.qeemblog.pojo.User;
import com.starQeem.qeemblog.service.MessageService;
import com.starQeem.qeemblog.mapper.MessageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starQeem.qeemblog.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

import static com.starQeem.qeemblog.util.constant.MESSAGE_PAGE_SIZE;
import static com.starQeem.qeemblog.util.constant.PAGE_NUM;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

    @Resource
    private MailUtil mailUtil;
    @Resource
    private MessageMapper messageMapper;

    @Value("${spring.mail.username}") //从yaml配置文件中获取
    private String myMail; //我自己的邮箱地址
    @Value("${spring.mail.myname}")
    private String myName;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public PageInfo<Message> getMessageList(Integer pageNum) {
        if (pageNum == null){
            pageNum = PAGE_NUM;
        }
        PageHelper.startPage(pageNum, MESSAGE_PAGE_SIZE);
        PageHelper.orderBy("create_time desc");
        List<Message> messageList = messageMapper.getMessageList();//获取根评论列表

        for (Message message : messageList){
            List<Message> replyList = messageMapper.getReplyList(message.getId());//获取根评论的子评论列表
            message.setReplyList(replyList);
        }

        return new PageInfo<>(messageList, MESSAGE_PAGE_SIZE);
    }

    @Override
    @Transactional
    public int saveMessage(Message message) {
        message.setCreateTime(new Date());//设置留言日期
        int i = messageMapper.saveMessage(message);//新增留言

        return i;
    }

    @Override
    public void sendMail(User user, Message message) throws MessagingException {


        if (user!=null){
            //如果是管理员发的评论
            if (message.getParentMessage().getId()==null || message.getParentMessage()==null){
                //如果是根评论
                //不需要发给自己邮件
                return;
            }else {
                //如果不是根评论，则给[我回复的对象]发一封提醒邮件
                Message parentMessage = messageMapper.selectById(message.getParentMessage().getId());//获取父评论
                Mail mail = new Mail(null, parentMessage.getEmail(), parentMessage.getNickname(), parentMessage.getContent(),
                        message.getNickname(), message.getContent(),
                        "/message", "你在《"+myName+"的博客-留言板》中的评论有了新的回复！");

                mailUtil.sendThymeleafEmail(mail);
            }
        }else {
            //如果不是管理员发的评论
            if (message.getParentMessage().getId()==null || message.getParentMessage()==null){
                //如果是根评论
                //发给我自己，提醒有人在留言板留言了
                Mail mail = new Mail(null, myMail, myName, null,
                        message.getNickname(), message.getContent(),
                        "/message","您在《"+myName+"的博客-留言板》中有了新的留言！");

                mailUtil.sendThymeleafEmail(mail);

            }else{
                //如果不是根评论
                //给回复者[回复的对象]发一份提醒邮件
                Message parentMessage = messageMapper.selectById(message.getParentMessage().getId());//获取父评论
                Mail mail = new Mail(null,parentMessage.getEmail(),parentMessage.getNickname(),
                        parentMessage.getContent(),message.getNickname(),message.getContent(),
                        "/message","您在《"+myName+"的博客-留言板》中的评论有了新的回复！");

                mailUtil.sendThymeleafEmail(mail);

            }

        }
    }

    @Override
    public Integer getMessageCount() {
        Long count = messageMapper.selectCount(null);
        return Integer.valueOf(Math.toIntExact(count));
    }
}




