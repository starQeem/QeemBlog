package com.starQeem.qeemblog.mapper;

import com.starQeem.qeemblog.pojo.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    List<Message> getMessageList();

    int saveMessage(Message message);

    List<Message> getReplyList(@Param("rootMessageId") Long rootMessageId);

}




