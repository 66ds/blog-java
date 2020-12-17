package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.FriendlyLinkDao;
import com.qianbing.blog.entity.FriendlyLinkEntity;
import com.qianbing.blog.service.FriendlyLinkService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


@Service("friendlyLinkService")
public class FriendlyLinkServiceImpl extends ServiceImpl<FriendlyLinkDao, FriendlyLinkEntity> implements FriendlyLinkService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<FriendlyLinkEntity> queryWrapper = new QueryWrapper<FriendlyLinkEntity>();
        String content = (String) params.get("content");
        if(!StringUtils.isEmpty(content)){
            queryWrapper .and(wrapper -> wrapper.like("link_name",content).or().like("link_desc", content));
        }
        IPage<FriendlyLinkEntity> page = this.page(
                new Query<FriendlyLinkEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<FriendlyLinkEntity> selectList() {
        //查询已经审核通过的友链
        List<FriendlyLinkEntity> friendlyLinkEntities = this.baseMapper.selectList(new QueryWrapper<FriendlyLinkEntity>().eq("link_allow", 0));
        return friendlyLinkEntities;
    }

}