package com.codermast.imagebedbackend.interceptor;

import com.codermast.imagebedbackend.context.BaseContext;
import com.codermast.imagebedbackend.propertiese.JWTProperties;
import com.codermast.imagebedbackend.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

// 用户登录拦截器
@Slf4j
@Component
public class JWTTokenLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前请求是否为动态方法
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 从请求头中获取 token 令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        if (token == null || token.isEmpty()) {
            // 不通过，响应 401 状态码
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("{\"message\":\"Token is empty\"}");
            return false;
        }
        try {
            Claims claims = JWTUtils.parseJWT(jwtProperties.getAdminSecretKey(), token);
            // TODO: 将 UID 抽离
            Long uid = Long.valueOf(claims.get("uid").toString());

            // 将解析出来的 uid 添加到 ThreadLocal 中
            BaseContext.setCurrentId(uid);
            // 登录成功放行当前用户
            return true;
        }catch (Exception e) {
            log.error(e.getMessage());
            // 不通过，响应 401 状态码
            response.setStatus(401);
            return false;
        }
    }
}
