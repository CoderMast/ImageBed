package com.codermast.imagebedbackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    // 上传图片
    boolean uploadImage(MultipartFile file) throws IOException;
}
