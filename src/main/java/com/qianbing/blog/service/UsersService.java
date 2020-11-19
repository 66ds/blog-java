package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.LoginVo;
import com.qianbing.blog.vo.RegisterVo;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-09 09:12:57
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R register(RegisterVo registerVo, HttpServletRequest httpServletRequest);

    R login(LoginVo vo);

    R getUserInfoById(Integer userId);
}

