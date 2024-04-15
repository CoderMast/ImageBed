package com.codermast.imagebedbackend.service.impl;

import com.codermast.imagebedbackend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

@Service
public class ImageServiceImpl implements ImageService {
    // 图片上传
    @Override
    public boolean uploadImage(MultipartFile file) throws IOException {
        // 上传文件的原始名称 —— 带文件后缀
        String originalFilename = file.getOriginalFilename();

        // 年份
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        // 文件前置文件目录
        File preUrl = new File("static" + File.separator + "images" + File.separator + year + File.separator + month + File.separator + day);

        // 图片文件对象
        File image = new File(preUrl + File.separator + originalFilename);
        // 如果文件目录不存在，则创建目录
        if (!preUrl.exists()) {
            preUrl.mkdirs();
        }

        // 这里是为了规范，本质上的 FileOutputStream 检测到文件不存在时，也会自动创建文件。
        // 如果文件不存在，则创建文件
        if (!image.exists()) {
            image.createNewFile();
        }

        InputStream inputStream = file.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(preUrl + File.separator + originalFilename);

        byte[] buffer = new byte[1024];
        int b;

        // 文件输出
        while ((b = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, b);
        }

        outputStream.close();
        inputStream.close();

        return true;
    }
}
