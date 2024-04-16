package com.codermast.imagebedbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codermast.imagebedbackend.entity.User;
import com.codermast.imagebedbackend.mapper.UserMapper;
import com.codermast.imagebedbackend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        queryWrapper.eq(User::getPassword, password);

        return this.getOne(queryWrapper);
    }
}
