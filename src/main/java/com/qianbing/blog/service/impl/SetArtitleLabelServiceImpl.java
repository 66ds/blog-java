package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.SetArtitleLabelDao;
import com.qianbing.blog.entity.SetArtitleLabelEntity;
import com.qianbing.blog.service.SetArtitleLabelService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("setArtitleLabelService")
public class SetArtitleLabelServiceImpl extends ServiceImpl<SetArtitleLabelDao, SetArtitleLabelEntity> implements SetArtitleLabelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SetArtitleLabelEntity> page = this.page(
                new Query<SetArtitleLabelEntity>().getPage(params),
                new QueryWrapper<SetArtitleLabelEntity>()
        );

        return new PageUtils(page);
    }

}