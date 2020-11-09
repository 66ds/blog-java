package com.qianbing.blog;

import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BlogJavaApplicationTests {

    @Autowired
    private UsersService usersService;
    @Test
    void contextLoads() {
        System.out.println((int)((Math.random()*9+1)*10000));
    }

}
