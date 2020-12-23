package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.UserAttentionConstrant;
import com.qianbing.blog.dao.UserAttentionDao;
import com.qianbing.blog.entity.UserAttentionEntity;
import com.qianbing.blog.entity.vo.WhoAttentionMeEntity;
import com.qianbing.blog.service.UserAttentionService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.common.CommonVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public R getWhoAttentionMeInfo(long userId) {
        List<WhoAttentionMeEntity> whoAttentionMeInfo = this.baseMapper.getWhoAttentionMeInfo(userId);
        List<CommonVo> collect = whoAttentionMeInfo.stream().map(item -> {
            List<CommonVo.User> list = new ArrayList<>();
            CommonVo commonVo = new CommonVo();
            String attentionIds = item.getAttentionIds();
            String userIds = item.getUserIds();
            String userNames = item.getUserNames();
            List<Long> ts = Arrays.asList(attentionIds.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            List<Long> longs = Arrays.asList(userIds.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            List<String> strings = Arrays.asList(userNames.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
            for (int i = 0; i < longs.size(); i++) {
                CommonVo.User user = new CommonVo.User();
                user.setUserId(longs.get(i));
                user.setUserName(strings.get(i));
                list.add(user);
            }
            commonVo.setIds(ts);
            commonVo.setCreateTime(item.getAttentionDate());
            commonVo.setUsers(list);
            return commonVo;
        }).collect(Collectors.toList());
        return R.ok().setData(collect);
    }

    @Override
    public R deleteWhoAttentionMeInfo(List<Long> attentionIds) {
        this.baseMapper.deleteWhoAttentionMeInfo(attentionIds);
        return  R.ok(UserAttentionConstrant.DELETE_SUCCESS);
    }


}