package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.service.ArticlesService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;




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

}