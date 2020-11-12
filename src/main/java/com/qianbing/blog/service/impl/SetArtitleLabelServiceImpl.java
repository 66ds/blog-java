package com.qianbing.blog.service.impl;

import com.qianbing.blog.dao.SetArtitleLabelDao;
import com.qianbing.blog.entity.SetArtitleLabelEntity;
import com.qianbing.blog.service.SetArtitleLabelService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    //查出文章对应得所有标签id
    @Override
    public List<Long> getLabelIds(Long articleId){
        List<SetArtitleLabelEntity> article_id = this.baseMapper.selectList(new QueryWrapper<SetArtitleLabelEntity>().eq("article_id", articleId));
        if(article_id != null && article_id.size()>0){
            List<Long> collect = article_id.stream().map(item -> {
                return item.getLabelId();
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

}