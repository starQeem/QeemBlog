package com.starQeem.qeemblog.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starQeem.qeemblog.pojo.*;
import com.starQeem.qeemblog.service.BlogService;
import com.starQeem.qeemblog.service.CommentService;
import com.starQeem.qeemblog.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;

    @Override
    public List<Comment> getRootCommentList(Long blogId) {
        List<Comment> rootCommentList = getBaseMapper().getRootCommentList(blogId);//获得所有根评论，未注入回复评论
        for (Comment rootComment : rootCommentList) {//为所有根评论注入回复评论
            List<Comment> replyList = getReplyList(rootComment.getId(), rootComment.getBlog().getId());//根据博客id和根评论id获取回复评论
            rootComment.setReplyCommentList(replyList);
        }

        return rootCommentList;
    }

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        blogService.update(Wrappers.<Blog>lambdaUpdate()
                .eq(Blog::getId,comment.getBlog().getId())
                .setSql("comment_count = comment_count + 1"));
        comment.setCreateTime(new Date());
        getBaseMapper().saveComment(comment);
    }

    public List<Comment> getReplyList(Long rootCommentId, Long blogId) {
        return getBaseMapper().getReplyList(rootCommentId, blogId);//根据博客id和根评论id获取回复评论
    }
    /*
    * 评论总数
    * */
    @Override
    public Integer getCommentCount() {
        return Math.toIntExact(getBaseMapper().selectCount(null));
    }


}




