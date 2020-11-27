package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.UserAttentionConstrant;
import com.qianbing.blog.dao.UserAttentionDao;
import com.qianbing.blog.entity.UserAttentionEntity;
import com.qianbing.blog.service.UserAttentionService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


@Service("userAttentionService")
public class UserAttentionServiceImpl extends ServiceImpl<UserAttentionDao, UserAttentionEntity> implements UserAttentionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserAttentionEntity> page = this.page(
                new Query<UserAttentionEntity>().getPage(params),
                new QueryWrapper<UserAttentionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R saveAttentionInfo(UserAttentionEntity userAttention) {
        //查看用户是否已关注
        UserAttentionEntity userAttentionEntity = this.baseMapper.selectOne(new QueryWrapper<UserAttentionEntity>().eq("user_id", userAttention.getUserId()).eq("attention_id", userAttention.getAttentionId()));
        if(!StringUtils.isEmpty(userAttentionEntity)){
            //如果已关注则取消关注
            int i = this.baseMapper.deleteById(userAttentionEntity);
            if(i<1){
                return R.error(UserAttentionConstrant.ATTENTION_SERVER_ERROR);
            }
            return R.ok(UserAttentionConstrant.ATTENTION_CANCEL_FOLLOW_SUCCESS);
        }
        //没关注则添加关注
        int insert = this.baseMapper.insert(userAttention);
        if(insert<1){
            return R.error(UserAttentionConstrant.ATTENTION_SERVER_ERROR);
        }
        return R.ok(UserAttentionConstrant.ATTENTION_ADD_FOLLOW_SUCCESS);
    }

    @Override
    public UserAttentionEntity selectAttentionInfo(UserAttentionEntity userAttention) {
        UserAttentionEntity userAttentionEntity = this.baseMapper.selectOne(new QueryWrapper<UserAttentionEntity>().eq("user_id", userAttention.getUserId()).eq("attention_id", userAttention.getAttentionId()));
        return userAttentionEntity;
    }

}