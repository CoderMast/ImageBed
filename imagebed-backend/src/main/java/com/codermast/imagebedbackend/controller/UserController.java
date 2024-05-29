package com.codermast.imagebedbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codermast.imagebedbackend.context.BaseContext;
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
        user.setCreateTime(LocalDateTime.now());
        user.setLoginTime(LocalDateTime.now());

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
        // 修改用户最后登录时间
        userByGet.setLoginTime(LocalDateTime.now());
        userService.updateById(userByGet);

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

    // 禁用用户
    @PutMapping("/disable")
    public Result<User> disableUser(@RequestBody User user) {
        if (user == null){
            return Result.error("用户为空！");
        }

        // true 为正常
        if (!user.getStatus()){
            // 状态已经是禁用
            return Result.success("已经禁用！");
        }
        user.setStatus(false);
        userService.updateById(user);
        return Result.success("禁用成功！");
    }

    // 启用用户
    @PutMapping("/active")
    public Result<User> activeUser(@RequestBody User user) {
        if (user == null){
            return Result.error("用户为空！");
        }

        // 已经处于激活状态
        if (user.getStatus()){
            return Result.success("已经激活！");
        }

        user.setStatus(true);
        userService.updateById(user);
        return Result.success("激活成功");
    }

    // 用户信息修改
    @PutMapping("/update")
    public Result<User> updateUser(@RequestBody User user) {
        if (user == null){
            return Result.error("用户为空！");
        }

        boolean b = userService.updateById(user);
        if (b){
            return Result.success(user);
        }else {
            return Result.error("修改失败！");
        }
    }

    // 退出登录
    @DeleteMapping("/logout")
    public Result<User> logout() {
        BaseContext.removeCurrentId();
        return Result.success("退出成功！");
    }

    // 获取用户详细信息
    @GetMapping("/{uid}")
    public Result<User> getUser(@PathVariable("uid") String uid) {
        User user = userService.getById(uid);
        if (user == null){
            return Result.error("用户不存在！");
        }else {
            return Result.success(user);
        }
    }
}
