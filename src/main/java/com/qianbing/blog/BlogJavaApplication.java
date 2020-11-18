package com.qianbing.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BlogJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogJavaApplication.class, args);
    }

}
