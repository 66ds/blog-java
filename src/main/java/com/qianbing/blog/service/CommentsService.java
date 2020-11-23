package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.entity.CommentsEntity;
import com.qianbing.blog.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-23 10:12:03
 */
public interface CommentsService extends IService<CommentsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CommentsEntity> selectListInfo(Long articleId);

    R addComments(CommentsEntity comments);
}

