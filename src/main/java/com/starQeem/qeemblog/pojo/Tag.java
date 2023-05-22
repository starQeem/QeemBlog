package com.starQeem.qeemblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Date: 2023/4/26 12:51
 * @author: Qeem
 */
@Data
@NoArgsConstructor
@TableName(value = "t_tag")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(exist = false)
    private List<Blog> blogList;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
