package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.LabelsEntity;
import com.qianbing.blog.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:12:50
 */
public interface LabelsService extends IService<LabelsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

