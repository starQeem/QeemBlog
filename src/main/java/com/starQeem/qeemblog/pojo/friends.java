package com.starQeem.qeemblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Date: 2023/6/18 20:06
 * @author: Qeem
 */
@Data
@TableName(value ="t_friends")
public class friends {
    @TableId(type = IdType.AUTO)
    private Long id;  //主键id
    private String name;  //网站名称
    private String address;  //网站地址
    private String avatar;  //头像地址
    private Date createTime; //添加时间
}
