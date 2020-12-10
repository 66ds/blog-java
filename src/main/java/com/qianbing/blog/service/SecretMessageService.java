package com.qianbing.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.SecretMessageEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.MessageVo;
import com.qianbing.blog.vo.MessagesVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-09 14:18:27
 */
public interface SecretMessageService extends IService<SecretMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MessageVo> selectMessageList(Integer id);

    R saveSecretMessage(SecretMessageEntity secretMessage);

    List<MessagesVo> selectMessagesList(Integer sendId, Integer id);
}

