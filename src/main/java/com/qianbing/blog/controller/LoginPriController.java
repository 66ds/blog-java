package com.qianbing.blog.controller;

import com.qianbing.blog.component.SmsComponent;
import com.qianbing.blog.constrant.SmsContrant;
import com.qianbing.blog.exception.BizCodeExcetionEnum;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.LoginVo;
import com.qianbing.blog.vo.RegisterVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/api/v1/pri/user")
public class LoginPriController {

    @Autowired
    private UsersService usersService;

    /**
     * 获取用户的个人信息
     * @param request
     * @return
     */
    @PostMapping("/info")
    @ResponseBody
    public R getUserInfoById(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        return usersService.getUserInfoById(userId);
    }



}
