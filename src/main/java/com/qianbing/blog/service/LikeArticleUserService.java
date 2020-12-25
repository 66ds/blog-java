package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.LikeArticleUserEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-20 09:51:18
 */
public interface LikeArticleUserService extends IService<LikeArticleUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R selectList(Long articleId, Integer userId);

    R likeArticle(Long articleId, Integer userId);

    R getWhoDigMeInfo(long longValue);

    R deleteWhoDigMeInfo(List<Map<String, Object>> likeIds);
}

