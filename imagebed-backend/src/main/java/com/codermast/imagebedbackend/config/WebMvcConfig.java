package com.codermast.imagebedbackend.config;

import com.codermast.imagebedbackend.interceptor.JWTTokenLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JWTTokenLoginInterceptor jwtTokenLoginInterceptor;

    /*
    *   静态资源放行
    * */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/","file:static/");
    }

    /*
    *   拦截器配置
    * */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenLoginInterceptor)
                // 添加拦截路径
                .addPathPatterns("/user/**")
                // 添加排除路径
                .excludePathPatterns("/user/login","/user/register");
    }
}
