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
        Map map = new HashMap();
        PageUtils pageUtils = usersService.queryPage(map);
        System.out.println(pageUtils.getList());
    }

}
