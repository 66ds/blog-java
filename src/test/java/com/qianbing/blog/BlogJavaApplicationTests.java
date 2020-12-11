package com.qianbing.blog;

import com.qianbing.blog.dao.SecretMessageDao;
import com.qianbing.blog.entity.SecretMessageEntity;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class BlogJavaApplicationTests {

    @Autowired
    private UsersService usersService;
    @Autowired
    private SecretMessageDao secretMessageDao;
    @Test
    void contextLoads() {
        List<SecretMessageEntity> secretMessageEntity = secretMessageDao.selectMessagesList(3, 4);
        System.out.println(secretMessageEntity);

    }

}
