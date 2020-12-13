package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.StayMessageDao;
import com.qianbing.blog.entity.StayMessageEntity;
import com.qianbing.blog.service.StayMessageService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("stayMessageService")
public class StayMessageServiceImpl extends ServiceImpl<StayMessageDao, StayMessageEntity> implements StayMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StayMessageEntity> page = this.page(
                new Query<StayMessageEntity>().getPage(params),
                new QueryWrapper<StayMessageEntity>()
        );

        return new PageUtils(page);
    }

}