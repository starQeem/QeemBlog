package com.starQeem.qeemblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@TableName(value = "t_type")
public class Type {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(exist = false)
    private List<Blog> blogList;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
