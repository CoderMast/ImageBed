package com.codermast.imagebedbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Long uid;
    private String username;
    private String password;
    private String email;
    private Boolean status;
    private LocalDateTime createTime;
    private LocalDateTime loginTime;
    private Boolean isAdmin;
}
