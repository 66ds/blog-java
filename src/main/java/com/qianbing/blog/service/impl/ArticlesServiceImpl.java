package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.ArticlesConstrant;
import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.service.ArticlesService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


@Service("articlesService")
public class ArticlesServiceImpl extends ServiceImpl<ArticlesDao, ArticlesEntity> implements ArticlesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ArticlesEntity> page = this.page(
                new Query<ArticlesEntity>().getPage(params),
                new QueryWrapper<ArticlesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R saveArticles(ArticlesEntity articlesEntity) {
        //查看当前标题是不是已存在
        QueryWrapper<ArticlesEntity> queryWrapper = new QueryWrapper<ArticlesEntity>().eq("article_title", articlesEntity.getArticleTitle());
        ArticlesEntity article = this.baseMapper.selectOne(queryWrapper);
        if(!StringUtils.isEmpty(article)){
            return R.error(ArticlesConstrant.ARTICLE_ALEARDY_EXIST);
        }
        articlesEntity.setArticleDate(new Date());
        articlesEntity.setArticleLikeCount(0L);
        articlesEntity.setArticleViews(0L);
        articlesEntity.setArticleCommentCount(0L);
        int count = this.baseMapper.insert(articlesEntity);
        if(count<1){
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        }
        return R.ok();
    }

}