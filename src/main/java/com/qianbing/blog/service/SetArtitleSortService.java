package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.SetArtitleSortEntity;
import com.qianbing.blog.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:13:08
 */
public interface SetArtitleSortService extends IService<SetArtitleSortEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

