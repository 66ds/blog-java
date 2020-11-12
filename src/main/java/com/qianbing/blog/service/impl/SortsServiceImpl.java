package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.SortsDao;
import com.qianbing.blog.entity.SortsEntity;
import com.qianbing.blog.service.SortsService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("sortsService")
public class SortsServiceImpl extends ServiceImpl<SortsDao, SortsEntity> implements SortsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SortsEntity> page = this.page(
                new Query<SortsEntity>().getPage(params),
                new QueryWrapper<SortsEntity>()
        );

        return new PageUtils(page);
    }

}