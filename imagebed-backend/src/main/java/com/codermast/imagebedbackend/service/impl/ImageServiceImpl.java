package com.codermast.imagebedbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codermast.imagebedbackend.context.BaseContext;
import com.codermast.imagebedbackend.entity.Image;
import com.codermast.imagebedbackend.mapper.ImageMapper;
import com.codermast.imagebedbackend.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    // 图片上传
    @Override
    public Image uploadImage(MultipartFile file) {
        Image imageRet = new Image();
        try {
            // 上传文件的原始名称 —— 带文件后缀
            String originalFilename = file.getOriginalFilename();
            // 上传文件的 MD5 值，该值永远不重复
            String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());

            if (originalFilename == null) {
                return imageRet;
            }
            // 文件后缀名
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            log.info(suffixName);
            // 年份
            Calendar calendar = Calendar.getInstance();
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

            // 文件前置文件目录
            File preDir = new File("static" + File.separator + "images" + File.separator + year + File.separator + month + File.separator + day);

            // 如果文件目录不存在，则创建目录
            if (!preDir.exists()) {
                preDir.mkdirs();
            }

            // 图片文件对象
            File image = new File(preDir + File.separator + md5 + suffixName);

            // 这里是为了规范，本质上的 FileOutputStream 检测到文件不存在时，也会自动创建文件。
            // 如果文件不存在，则创建文件
            if (!image.exists()) {
                image.createNewFile();
            }

            InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(preDir + File.separator + md5 + suffixName);

            byte[] buffer = new byte[1024];
            int b;

            // 文件输出
            while ((b = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);
            }

            outputStream.close();
            inputStream.close();

            // 获取图片的MD5
            LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Image::getMd5, md5);

            // 判断 MD5 是否存在
            Image existImage = this.getOne(queryWrapper);
            if (existImage != null) {
                return existImage;
            }

            // 构建图片实体
            Image savaImage = new Image();
            savaImage.setUrl(preDir.getPath() + File.separator + md5 + suffixName);
            savaImage.setMd5(md5);
            savaImage.setUploadTime(LocalDateTime.now());
            if (BaseContext.getCurrentId() != null) {
                savaImage.setAuthor(BaseContext.getCurrentId());
            } else {
                // 如果当前是游客，则设置作者为 -1
                savaImage.setAuthor(-1L);
            }
            this.save(savaImage);
            imageRet = savaImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageRet;
    }

    // 查询所有图片
    @Override
    public List<Image> getAll() {
        LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
        return this.list(queryWrapper);
    }

    // 清空图片
    @Override
    public boolean removeAll() {
        LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
        this.remove(queryWrapper);
        return true;
    }
}
