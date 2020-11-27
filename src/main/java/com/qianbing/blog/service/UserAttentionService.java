package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.UserAttentionEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;

import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-27 22:57:08
 */
public interface UserAttentionService extends IService<UserAttentionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R saveAttentionInfo(UserAttentionEntity userAttention);

    UserAttentionEntity selectAttentionInfo(UserAttentionEntity userAttention);
}

