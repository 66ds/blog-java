package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.ArticlesConstrant;
import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.service.ArticlesService;
import com.qianbing.blog.utils.Constant;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        QueryWrapper<ArticlesEntity> queryWrapper = new QueryWrapper<ArticlesEntity>();
        //设置排序字段
        params.put(Constant.ORDER_FIELD,"article_date");
        //设置升序
        params.put(Constant.ORDER,"asc");
        //设置其他搜索参数
        String support = (String) params.get("support");
        if(!StringUtils.isEmpty(support)){
            queryWrapper.eq("article_up ",support);
        }
        String type = (String) params.get("type");
        if(!StringUtils.isEmpty(type)){
            queryWrapper.eq("article_type",type);
        }
        String up = (String) params.get("up");
        if(!StringUtils.isEmpty(up)){
            queryWrapper.eq("article_up",up);
        }
        String name = (String) params.get("name");
        if(!StringUtils.isEmpty(name)){
            queryWrapper.and((obj)->{
                obj.like("article_title",name).or().like("article_content_origin",name);
            });
        }
        IPage<ArticlesEntity> page = this.page(
                new Query<ArticlesEntity>().getPage(params),
                queryWrapper
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

    @Override
    public R updateArticles(ArticlesEntity articles) {
        ArticlesEntity articleEnty = this.baseMapper.selectById(articles.getArticleId());
        BeanUtils.copyProperties(articles,articleEnty);
        int i = this.baseMapper.updateById(articleEnty);
        if(i<1){
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        }
        return R.ok();
    }

    @Override
    public R delete(Long articleId) {
        int i = this.baseMapper.deleteById(articleId);
        if(i<1){
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        }else{
            return R.ok();
        }
    }

}