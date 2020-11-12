package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.SetArtitleSortDao;
import com.qianbing.blog.entity.SetArtitleSortEntity;
import com.qianbing.blog.service.SetArtitleSortService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service("setArtitleSortService")
public class SetArtitleSortServiceImpl extends ServiceImpl<SetArtitleSortDao, SetArtitleSortEntity> implements SetArtitleSortService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SetArtitleSortEntity> page = this.page(
                new Query<SetArtitleSortEntity>().getPage(params),
                new QueryWrapper<SetArtitleSortEntity>()
        );

        return new PageUtils(page);
    }

}