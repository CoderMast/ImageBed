package com.codermast.imagebedbackend.controller;


import com.alibaba.fastjson2.JSON;
import com.codermast.imagebedbackend.entity.Image;
import com.codermast.imagebedbackend.service.ImageService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final List<String> imgSuffixNames = Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "ico");

    @Autowired
    private ImageService imageService;

    // 图片上传
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "文件为空";
        }

        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        // 文件后缀名
        String suffix = split[split.length - 1];

        if (!imgSuffixNames.contains(suffix)) {
            return "上传的文件不是图片";
        }

        // 这里已经可以确保上传的文件是图片了，开始执行图片上传逻辑
        Image image = imageService.uploadImage(file);

        if (image == null) {
            return "fail";
        }


        // 返回上传结果
        return JSON.toJSONString(image);
    }


    // 查看图片列表
    @GetMapping("/list")
    public List<Image> list() {
        List<Image> list = imageService.getAll();
        return list;
    }
}