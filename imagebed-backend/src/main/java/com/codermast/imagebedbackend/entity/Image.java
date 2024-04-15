package com.codermast.imagebedbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("images")
public class Image {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String url;
    private String md5;
}
