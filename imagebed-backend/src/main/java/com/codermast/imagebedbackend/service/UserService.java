package com.codermast.imagebedbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codermast.imagebedbackend.entity.User;

public interface UserService extends IService<User> {
    User login(User user);
}
