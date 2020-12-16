package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.LabelsDao;
import com.qianbing.blog.entity.LabelsEntity;
import com.qianbing.blog.service.LabelsService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


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

    @Override
    public List<LabelsEntity> selectListInfo(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper<LabelsEntity>();
        if(!StringUtils.isEmpty(userId)){
            queryWrapper.eq("user_id",userId);
        }
        List<LabelsEntity> labelsEntities = this.baseMapper.selectList(queryWrapper);
        return labelsEntities;
    }

}