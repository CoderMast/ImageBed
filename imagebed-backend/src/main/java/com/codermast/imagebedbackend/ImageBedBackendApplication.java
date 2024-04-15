package com.codermast.imagebedbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@MapperScan("com.codermast.imagebedbackend.mapper")
@SpringBootApplication
public class ImageBedBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageBedBackendApplication.class, args);
    }

}