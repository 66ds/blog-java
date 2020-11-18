package com.qianbing.blog.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.ArticlesVo;


import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-10 15:44:24
 */
public interface ArticlesService extends IService<ArticlesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R saveArticles(ArticlesVo vo);

    R updateArticles(ArticlesVo articlesVo);

    R delete(Long articleId);

    ArticlesEntity findArticleById(Long articleId);

    R deleteArticle(Long articleId);

    Set<String> getTimeList(String userId);

    PageUtils selectListByTime(Map<String, Object> params, String time);
}

