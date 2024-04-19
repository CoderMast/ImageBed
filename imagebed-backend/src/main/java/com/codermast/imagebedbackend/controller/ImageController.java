package com.codermast.imagebedbackend.controller;

import com.codermast.imagebedbackend.entity.Image;
import com.codermast.imagebedbackend.entity.Result;
import com.codermast.imagebedbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/image")
public class ImageController {
    // 图片后缀名列表
    private static final List<String> imgSuffixNames = Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "ico");

    @Autowired
    private ImageService imageService;

    // 图片上传
    @PostMapping("/upload")
    public Result<List<Image>> uploadImage(@RequestParam("file") MultipartFile... files){
        List<Image> ret = new ArrayList<>();
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                //return Result.error("文件为空");
                ret.add(null);
                break;
            }

            String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");

            // 文件后缀名
            String suffix = split[split.length - 1];

            if (!imgSuffixNames.contains(suffix)) {
                //return Result.error("上传的文件不是图片");
                ret.add(null);
                break;
            }

            // 这里已经可以确保上传的文件是图片了，开始执行图片上传逻辑
            Image image = imageService.uploadImage(file);
            ret.add(image);
        }
        // 返回上传结果
        return Result.success(ret);
    }


    // 查看图片列表
    @GetMapping("/list")
    public Result<List<Image>> list() {
        List<Image> list = imageService.getAll();
        return Result.success(list);
    }

    // 删除指定图片
    @DeleteMapping("/remove")
    public Result<Image> remove(@RequestBody Image image) {
        boolean isTrue = imageService.removeById(image);
        if (isTrue){
            return Result.success("删除成功");
        }else {
            return Result.error("删除失败");
        }
    }

    // 批量删除图片
    @DeleteMapping("/removeList")
    public Result<Image> removeList(@RequestBody List<Image> images) {
        boolean isTrue = imageService.removeByIds(images);
        if (isTrue){
            return Result.success("删除成功");
        }else {
            return Result.error("删除失败");
        }
    }

    // 清空图片
    @DeleteMapping("/removeAll")
    public Result<Image> removeAll() {
        boolean isTrue =  imageService.removeAll();
        if (isTrue){
            return Result.success("删除成功");
        }else {
            return Result.error("删除失败");
        }
    }
}
