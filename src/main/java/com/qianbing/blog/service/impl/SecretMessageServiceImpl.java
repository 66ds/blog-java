package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.SecretMessageDao;
import com.qianbing.blog.dao.UserAttentionDao;
import com.qianbing.blog.dao.UsersDao;
import com.qianbing.blog.entity.SecretMessageEntity;
import com.qianbing.blog.entity.UserAttentionEntity;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.SecretMessageService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("secretMessageService")
public class SecretMessageServiceImpl extends ServiceImpl<SecretMessageDao, SecretMessageEntity> implements SecretMessageService {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UserAttentionDao userAttentionDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SecretMessageEntity> page = this.page(
                new Query<SecretMessageEntity>().getPage(params),
                new QueryWrapper<SecretMessageEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MessageVo> selectMessageList(Integer id) {
        List<SecretMessageEntity> recived = this.baseMapper.selectList(new QueryWrapper<SecretMessageEntity>().eq("receive_id", id)).stream().sorted((men1, men2) ->
                (int) (men2.getCreateTime().getTime() - men1.getCreateTime().getTime())
        ).filter(distinctByKey(b -> b.getSendId())).collect(Collectors.toList());
        List<MessageVo> collect = recived.stream().map(item -> {
            MessageVo messageVo = new MessageVo();
            UsersEntity usersEntity = usersDao.selectById(item.getSendId());
            messageVo.setUserId(usersEntity.getUserId());
            messageVo.setUserName(usersEntity.getUserNickname());
            messageVo.setUserImg(usersEntity.getUserProfilePhoto());
            messageVo.setContent(item.getMessageContent());
            messageVo.setCreateTime(item.getCreateTime());
            //判断是否已经关注
            UserAttentionEntity userAttentionEntity = userAttentionDao.selectOne(new QueryWrapper<UserAttentionEntity>().eq("user_id", id).eq("attention_id", item.getSendId()));
            messageVo.setAttention(userAttentionEntity == null ? false : true);
            return messageVo;
        }).sorted((men1, men2) ->
                (int) (men2.getCreateTime().getTime() - men1.getCreateTime().getTime())
        ).collect(Collectors.toList());
        return collect;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}