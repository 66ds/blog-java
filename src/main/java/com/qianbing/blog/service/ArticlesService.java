package com.qianbing.blog.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;


import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-10 15:44:24
 */
public interface ArticlesService extends IService<ArticlesEntity> {

    PageUtils queryPage(Map<String, Object> params);
    R saveArticles(ArticlesEntity articlesEntity);
}

