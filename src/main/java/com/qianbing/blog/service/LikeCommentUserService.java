package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.LikeCommentUserEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;

import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-24 20:58:37
 */
public interface LikeCommentUserService extends IService<LikeCommentUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R selectList(Long commentId, Integer userId);

    R likeComment(Long commentId, Integer userId);
}

