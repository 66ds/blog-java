package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.SortsConstrant;
import com.qianbing.blog.dao.SetArtitleSortDao;
import com.qianbing.blog.dao.SortsDao;
import com.qianbing.blog.entity.SetArtitleSortEntity;
import com.qianbing.blog.entity.SortsEntity;
import com.qianbing.blog.service.SortsService;
import com.qianbing.blog.utils.Constant;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


@Service("sortsService")
public class SortsServiceImpl extends ServiceImpl<SortsDao, SortsEntity> implements SortsService {

    @Autowired
    private SortsDao sortsDao;

    @Autowired
    private SetArtitleSortDao setArtitleSortDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SortsEntity> queryWrapper = new QueryWrapper<SortsEntity>();
        queryWrapper.eq("user_id", params.get("userId"));
        //设置排序字段
        params.put(Constant.ORDER_FIELD, "sort_time");
        //设置升序
        params.put(Constant.ORDER, "asc");
        //设置其他搜索参数
        Integer sortId = (Integer) params.get("sortId");
        if(!StringUtils.isEmpty(sortId)){
            queryWrapper.eq("sort_id",sortId);
        }
        String name = (String) params.get("name");
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.and((obj) -> {
                obj.like("sort_name", name).or().like("sort_alias", name).or().like("sort_description", name);
            });
        }
        IPage<SortsEntity> page = this.page(
                new Query<SortsEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }


    @Override
    public List<SortsEntity> findCatagorysByUserId(Integer userId) {
        List<SortsEntity> sortsEntities = sortsDao.selectList(new QueryWrapper<SortsEntity>().eq("user_id", userId));
        List<SortsEntity> list = sortsEntities.stream().filter(sortsEntity ->
                sortsEntity.getParentSortId() == 0
        ).map(menu -> {
            menu.setChildren(getChildrenTree(menu, sortsEntities));
            return menu;
        }).sorted((men1, men2) ->
                (int) (men1.getSortTime().getTime() - men2.getSortTime().getTime())
        ).collect(Collectors.toList());
        return list;
    }

    @Override
    public R saveSort(SortsEntity sorts) {
        sorts.setSortTime(new Date());
        QueryWrapper queryWrapper = new QueryWrapper<SortsEntity>();
        queryWrapper.eq("sort_name",sorts.getSortName());
        SortsEntity sortsEntity = this.baseMapper.selectOne(queryWrapper);
        if(!StringUtils.isEmpty(sortsEntity)){
            return R.error(SortsConstrant.SORT_AREADY_EXIST);
        }
        int insert = this.baseMapper.insert(sorts);
        if(insert<1){
            return R.error(SortsConstrant.SORT_SERVER_ERROR);
        }
        return R.ok();
    }

    @Override
    public R updateSorts(SortsEntity sorts) {
        QueryWrapper<SortsEntity> queryWrapper = new QueryWrapper<SortsEntity>();
        queryWrapper.eq("sort_name", sorts.getSortName());
        queryWrapper.and((obj) -> {
            obj.ne("sort_id", sorts.getSortId());
        });
        SortsEntity sortEntity = this.baseMapper.selectOne(queryWrapper);
        if(!StringUtils.isEmpty(sortEntity)){
            return R.error(SortsConstrant.SORT_AREADY_EXIST);
        }
        sorts.setSortTime(new Date());
        int insert = this.baseMapper.updateById(sorts);
        if(insert<1){
            return R.error(SortsConstrant.SORT_SERVER_ERROR);
        }
        return R.ok();
    }

    @Override
    public R deleteSorts(Long sortId,Integer userId) {
        //根据sortId先查询
        SortsEntity sortsEntity = this.baseMapper.selectById(sortId);
        //查询该用户的所有分类
        List<SortsEntity> sortsEntities = this.baseMapper.selectList(new QueryWrapper<SortsEntity>().eq("user_id", userId));
        List<Long> longs = new ArrayList<>();
        List<SortsEntity> childrenTree = getChildrenTree(sortsEntity, sortsEntities);
        List<Long> sortIdsTree = getSortIdsTree(longs, childrenTree);
        //把自己的分类id加上去
        sortIdsTree.add(sortsEntity.getSortId());
        //判断所有的分类id中是不是有文章,如果有则不删除
        List<SetArtitleSortEntity> entities = setArtitleSortDao.selectList(new QueryWrapper<SetArtitleSortEntity>().in("sort_id", sortIdsTree));
        if(entities.size()>0){
            return R.error(SortsConstrant.SORT_ARTICLE_AREADY_EXIST);
        }
        //删除所有分类id
        int i = this.baseMapper.deleteBatchIds(sortIdsTree);
        if(i<1){
            return R.error(SortsConstrant.SORT_SERVER_ERROR);
        }
        return R.ok();
    }

    //根据普通用户和管理员来实现权限的管理,当未登录时显示最火的10条博客
    private List<SortsEntity> getChildrenTree(SortsEntity sortsEntity, List<SortsEntity> sortsEntities) {
        List<SortsEntity> list = sortsEntities.stream().filter(sortsEntity1 ->
                sortsEntity1.getParentSortId() == sortsEntity.getSortId()
        ).map(menu -> {
            menu.setChildren(getChildrenTree(menu, sortsEntities));
            return menu;
        }).sorted((men1, men2) ->
                (int) (men1.getSortTime().getTime() - men2.getSortTime().getTime())
        ).collect(Collectors.toList());
        return list;
    }

    //获取所有的下级Id包括自己
    private List<Long> getSortIdsTree(List<Long> list,List<SortsEntity> sortsEntities){
        sortsEntities.stream().forEach(item->{
            list.add(item.getSortId());
            if(item.getChildren() != null){
                getSortIdsTree(list,item.getChildren());
            }
        });
        return list;
    }

}