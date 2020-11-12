package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.LabelsDao;
import com.qianbing.blog.entity.LabelsEntity;
import com.qianbing.blog.service.LabelsService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("labelsService")
public class LabelsServiceImpl extends ServiceImpl<LabelsDao, LabelsEntity> implements LabelsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LabelsEntity> page = this.page(
                new Query<LabelsEntity>().getPage(params),
                new QueryWrapper<LabelsEntity>()
        );

        return new PageUtils(page);
    }

}