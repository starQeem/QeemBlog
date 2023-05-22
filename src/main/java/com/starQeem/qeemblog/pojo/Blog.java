package com.starQeem.qeemblog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName(value ="t_blog")
public class Blog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title; //标题
    private String content; //博客内容
    private String firstPicture;//首图
    private String flag; //标记
    private String description; //博客描述
    private Integer views; //浏览次数
    private boolean shareStatement; //转载是否开启
    private boolean commentabled; //评论是否开启
    private boolean published; //是否发布
    private boolean recommend; //是否推荐
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

    private String tagIds;

    @TableField(exist = false)
    private Type type;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private List<Tag> tag;
    @TableField(exist = false)
    private List<Comment> commentList;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



}
