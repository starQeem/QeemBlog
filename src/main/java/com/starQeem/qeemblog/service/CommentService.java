package com.starQeem.qeemblog.service;

import com.starQeem.qeemblog.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starQeem.qeemblog.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CommentService extends IService<Comment> {

    List<Comment> getRootCommentList(Long blogId);

    void saveComment(Comment comment);

    List<Comment> getReplyList(Long rootCommentId,Long blogId);

    Integer getCommentCount();
}
