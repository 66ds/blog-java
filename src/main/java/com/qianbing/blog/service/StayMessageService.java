package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.StayMessageEntity;
import com.qianbing.blog.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-13 20:46:28
 */
public interface StayMessageService extends IService<StayMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

