package com.codermast.imagebedbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codermast.imagebedbackend.entity.Result;
import com.codermast.imagebedbackend.entity.User;
import com.codermast.imagebedbackend.propertiese.JWTProperties;
import com.codermast.imagebedbackend.service.UserService;
import com.codermast.imagebedbackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTProperties jwtProperties;
    // 用户注册
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        if (user == null){
            return Result.error("用户信息错误");
        }

        // 这里保证 user != null
        // 先查是否有用户名相同的
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User one = userService.getOne(queryWrapper);

        if (one != null){
            return Result.error("用户名已经存在！");
        }
        // 此时也没有同名用户，故可以开始配置注册
        user.setStatus(true);
        user.setIsAdmin(true);
        user.setCreate_time(LocalDateTime.now());
        user.setLogin_time(LocalDateTime.now());

        // 注册
        boolean save = userService.save(user);
        if (save) {
            return Result.success(user);
        }else {
            return Result.error("注册失败");
        }
    }

    // 用户登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        if (user == null){
            return Result.error("登录失败！");
        }

        // 此时用户对象不为 null
        User userByGet = userService.login(user);

        if (userByGet == null){
            return Result.error("用户名或密码错误！");
        }

        // 此处时说明也已经验证成功了用户信息
        // 登陆成功则添加 JWT 令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", userByGet.getUid());
        String token = JWTUtils.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        // 直接返回 JWT 令牌，通过前端将其添加到请求头中
        // TODO:应该使用 UserLoginVo 对象来传递
        return Result.success(token);
    }
}
