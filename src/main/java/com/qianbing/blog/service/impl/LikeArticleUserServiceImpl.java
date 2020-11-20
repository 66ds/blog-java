package com.qianbing.blog.service.impl;
import com.qianbing.blog.constrant.LikeArticleUserConstrant;
import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.dao.LikeArticleUserDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.entity.LikeArticleUserEntity;
import com.qianbing.blog.service.LikeArticleUserService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("likeArticleUserService")
public class LikeArticleUserServiceImpl extends ServiceImpl<LikeArticleUserDao, LikeArticleUserEntity> implements LikeArticleUserService {

    @Autowired
    private ArticlesDao articlesDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LikeArticleUserEntity> page = this.page(
                new Query<LikeArticleUserEntity>().getPage(params),
                new QueryWrapper<LikeArticleUserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R selectList(Long articleId, Integer userId) {
        LikeArticleUserEntity likeArticleUserEntity = this.baseMapper.selectOne(new QueryWrapper<LikeArticleUserEntity>().eq("article_id", articleId).eq("user_id", userId));
        if(StringUtils.isEmpty(likeArticleUserEntity)){
            return R.ok();
        }
        return R.ok().setData(likeArticleUserEntity);
    }

    @Transactional
    @Override
    public R likeArticle(Long articleId, Integer userId) {
        //如果已经点赞则取消,并且将文章的数量减1
        ArticlesEntity articlesEntity = articlesDao.selectById(articleId);
        LikeArticleUserEntity likeArticleUserEntity = this.baseMapper.selectOne(new QueryWrapper<LikeArticleUserEntity>().eq("article_id", articleId).eq("user_id", userId));
        if(!StringUtils.isEmpty(likeArticleUserEntity)){
            this.baseMapper.deleteById(likeArticleUserEntity);
            articlesEntity.setArticleLikeCount(articlesEntity.getArticleLikeCount()-1);
            int num = articlesDao.updateById(articlesEntity);
            if(num < 1){
                return R.error(LikeArticleUserConstrant.LIKEARTICLEUSER_SERVER_ERROR);
            }
            return R.ok();
        }
        //没有点赞则添加,并且修改文章的赞数量
        LikeArticleUserEntity entity = new LikeArticleUserEntity();
        entity.setArticleId(articleId);
        entity.setLikeDate(new Date());
        entity.setUserId(userId.longValue());
        int insert = this.baseMapper.insert(entity);
        //修改文章的赞数量+1
        articlesEntity.setArticleLikeCount(articlesEntity.getArticleLikeCount()+1);
        int number = articlesDao.updateById(articlesEntity);
        if(insert < 1 || number < 1){
            return R.error(LikeArticleUserConstrant.LIKEARTICLEUSER_SERVER_ERROR);
        }
        return R.ok().setData(entity);
    }

}