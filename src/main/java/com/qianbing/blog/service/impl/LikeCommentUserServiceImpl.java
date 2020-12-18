package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.LikeCommentUserConstrant;
import com.qianbing.blog.dao.CommentsDao;
import com.qianbing.blog.dao.LikeCommentUserDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.entity.CommentsEntity;
import com.qianbing.blog.entity.LikeArticleUserEntity;
import com.qianbing.blog.entity.LikeCommentUserEntity;
import com.qianbing.blog.service.LikeCommentUserService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("likeCommentUserService")
public class LikeCommentUserServiceImpl extends ServiceImpl<LikeCommentUserDao, LikeCommentUserEntity> implements LikeCommentUserService {
    @Autowired
    private CommentsDao commentsDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LikeCommentUserEntity> page = this.page(
                new Query<LikeCommentUserEntity>().getPage(params),
                new QueryWrapper<LikeCommentUserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R selectList(Long articleId, Integer userId) {
        //获取文章对应得所有评论
        List<CommentsEntity> commentsEntities = commentsDao.selectList(new QueryWrapper<CommentsEntity>().eq("article_id", articleId).eq("user_id", userId));
        //获取用户所赞的评论
        List<LikeCommentUserEntity> likeCommentUserEntities = this.baseMapper.selectList(new QueryWrapper<LikeCommentUserEntity>().eq("user_id", userId));
        List<Long> collect = commentsEntities.stream().map(item -> {
            return item.getCommentId();
        }).collect(Collectors.toList());
        List<Long> collect1 = likeCommentUserEntities.stream().map(item -> {
            return item.getCommentId();
        }).collect(Collectors.toList());
        //取出交集
        collect.retainAll(collect1);
        return R.ok().setData(collect);
    }

    @Transactional
    @Override
    public R likeComment(Long commentId, Integer userId) {
        //如果已经点赞则取消,并且将文章的评论数减1
        CommentsEntity commentsEntity = commentsDao.selectById(commentId);
        LikeCommentUserEntity likeCommentUserEntity = this.baseMapper.selectOne(new QueryWrapper<LikeCommentUserEntity>().eq("comment_id", commentId).eq("user_id", userId));
        if(!StringUtils.isEmpty(likeCommentUserEntity)){
            int i = this.baseMapper.deleteById(likeCommentUserEntity);
            commentsEntity.setCommentLikeCount(commentsEntity.getCommentLikeCount()-1);
            int num = commentsDao.updateById(commentsEntity);
            if(num < 1 || i<1){
                return R.error(LikeCommentUserConstrant.LIKECOMMENTUSER_SERVER_ERROR);
            }
            return R.ok();
        }
        //没有点赞则添加,并且修改文章的赞数量
        LikeCommentUserEntity entity = new LikeCommentUserEntity();
        entity.setCommentId(commentId);
        entity.setLikeDate(new Date());
        entity.setUserId(userId.longValue());
        //默认未读
        entity.setIsRead(0L);
        int insert = this.baseMapper.insert(entity);
        //修改文章的评论数量+1
        commentsEntity.setCommentLikeCount(commentsEntity.getCommentLikeCount()+1);
        int number = commentsDao.updateById(commentsEntity);
        if(insert < 1 || number < 1){
            return R.error(LikeCommentUserConstrant.LIKECOMMENTUSER_SERVER_ERROR);
        }
        return R.ok().setData(entity);
    }


}