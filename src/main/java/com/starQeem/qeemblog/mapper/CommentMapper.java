package com.starQeem.qeemblog.mapper;

import com.starQeem.qeemblog.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CommentMapper extends BaseMapper<Comment> {


    List<Comment> getRootCommentList(@Param("blogId") Long blogId);

    void saveComment(Comment comment);

    List<Comment> getReplyList(@Param("rootCommentId") Long rootCommentId,@Param("blogId") Long blogId);
}




