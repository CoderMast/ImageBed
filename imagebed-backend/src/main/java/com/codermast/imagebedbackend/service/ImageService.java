package com.codermast.imagebedbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codermast.imagebedbackend.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService extends IService<Image> {
    // 上传图片
    Image uploadImage(MultipartFile file);

    List<Image> getAll();

    boolean removeAll();
}
