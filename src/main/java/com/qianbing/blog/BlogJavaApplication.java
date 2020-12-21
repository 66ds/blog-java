package com.qianbing.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class BlogJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogJavaApplication.class, args);
    }

}
