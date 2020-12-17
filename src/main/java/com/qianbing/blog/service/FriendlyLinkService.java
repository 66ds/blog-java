package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.FriendlyLinkEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-16 20:43:29
 */
public interface FriendlyLinkService extends IService<FriendlyLinkEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<FriendlyLinkEntity> selectList();

    R updateLinkInfo(FriendlyLinkEntity friendlyLink);
}

