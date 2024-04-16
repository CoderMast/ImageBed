package com.codermast.imagebedbackend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codermast.imagebedbackend.context.BaseContext;
import com.codermast.imagebedbackend.entity.Image;
import com.codermast.imagebedbackend.mapper.ImageMapper;
import com.codermast.imagebedbackend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    // 图片上传
    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
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

        // 获取图片的MD5
        String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
        LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Image::getMd5,md5);

        // 判断 MD5 是否存在
        Image existImage = this.getOne(queryWrapper);
        if (existImage != null){
            return existImage;
        }

        // 构建图片实体
        Image savaImage = new Image();
        savaImage.setUrl(preUrl.getPath() + File.separator + originalFilename);
        savaImage.setMd5(md5);
        savaImage.setAuthor(BaseContext.getCurrentId());
        this.save(savaImage);

        return savaImage;
    }

    // 查询所有图片
    @Override
    public List<Image> getAll() {
        LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
        return this.list(queryWrapper);
    }
}
