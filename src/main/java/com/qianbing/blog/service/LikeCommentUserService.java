package com.qianbing.blog.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.LikeCommentUserEntity;
import com.qianbing.blog.utils.PageUtils;


import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-20 09:18:33
 */
public interface LikeCommentUserService extends IService<LikeCommentUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

