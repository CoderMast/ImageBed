package com.codermast.imagebedbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codermast.imagebedbackend.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService extends IService<Image> {
    // 上传图片
    List<Image> uploadImage(MultipartFile... file) throws IOException;

    List<Image> getAll();
}
