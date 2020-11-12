package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.SetArtitleLabelEntity;
import com.qianbing.blog.utils.PageUtils;


import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:13:08
 */
public interface SetArtitleLabelService extends IService<SetArtitleLabelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Long> getLabelIds(Long articleId);
}

