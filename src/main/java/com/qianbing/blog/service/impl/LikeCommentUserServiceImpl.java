package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.LikeCommentUserDao;
import com.qianbing.blog.entity.LikeCommentUserEntity;
import com.qianbing.blog.service.LikeCommentUserService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("likeCommentUserService")
public class LikeCommentUserServiceImpl extends ServiceImpl<LikeCommentUserDao, LikeCommentUserEntity> implements LikeCommentUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LikeCommentUserEntity> page = this.page(
                new Query<LikeCommentUserEntity>().getPage(params),
                new QueryWrapper<LikeCommentUserEntity>()
        );

        return new PageUtils(page);
    }

}