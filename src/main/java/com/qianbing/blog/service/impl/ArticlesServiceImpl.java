package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.ArticlesConstrant;
import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.dao.LabelsDao;
import com.qianbing.blog.dao.SetArtitleSortDao;
import com.qianbing.blog.dao.SortsDao;
import com.qianbing.blog.entity.*;
import com.qianbing.blog.service.ArticlesService;
import com.qianbing.blog.service.LabelsService;
import com.qianbing.blog.service.SetArtitleLabelService;
import com.qianbing.blog.utils.Constant;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.ArticlesVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("articlesService")
public class ArticlesServiceImpl extends ServiceImpl<ArticlesDao, ArticlesEntity> implements ArticlesService {

    @Autowired
    private LabelsService labelsService;

    @Autowired
    private SetArtitleLabelService setArtitleLabelService;

    @Autowired
    private LabelsDao labelsDao;

    @Autowired
    private SetArtitleSortDao setArtitleSortDao;

    @Autowired
    private SortsDao sortsDao;


//    @Cacheable(value = {"articles"}, key = "#root.methodName",sync = true)//代表当前的结果需要缓存,如果缓存中有,方法都不调用,没有就调用方法
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ArticlesEntity> queryWrapper = new QueryWrapper<ArticlesEntity>();
        //设置排序字段
        params.put(Constant.ORDER_FIELD, "article_date");
        //设置升序
        params.put(Constant.ORDER, "desc");
        Object userId = params.get("userId");
        if(!StringUtils.isEmpty(userId)){
            queryWrapper.eq("user_id",params.get("userId"));
        }
        //设置其他搜索参数
        String support = (String) params.get("support");
        if (!StringUtils.isEmpty(support)) {
            queryWrapper.eq("article_up ", support);
        }
        String type = (String) params.get("type");
        if (!StringUtils.isEmpty(type)) {
            queryWrapper.eq("article_type", type);
        }
        String up = (String) params.get("up");
        if (!StringUtils.isEmpty(up)) {
            queryWrapper.eq("article_up", up);
        }
        String name = (String) params.get("name");
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.and((obj) -> {
                obj.like("article_title", name).or().like("article_content_origin", name);
            });
        }
        IPage<ArticlesEntity> page = this.page(
                new Query<ArticlesEntity>().getPage(params),
                queryWrapper
        );
        List<ArticlesEntity> records = page.getRecords();
        List<ArticlesEntity> articlesEntities = records.stream().map(item -> {
            //根据id查出所有的标签
            List<Long> labelIds = setArtitleLabelService.getLabelIds(item.getArticleId());
            List<LabelsEntity> labelsEntities = labelsDao.selectList(new QueryWrapper<LabelsEntity>().in("label_id", labelIds));
            item.setLabelsEntityList(labelsEntities);
            //添加分类名称
            SetArtitleSortEntity setArtitleSortEntity = setArtitleSortDao.selectOne(new QueryWrapper<SetArtitleSortEntity>().eq("article_id", item.getArticleId()));
            SortsEntity sortsEntity = sortsDao.selectById(setArtitleSortEntity.getSortId());
            item.setSortName(sortsEntity.getSortName());
            return item;
        }).collect(Collectors.toList());
         page.setRecords(articlesEntities);
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public R saveArticles(ArticlesVo vo) {
        //查看当前标题是不是已存在
        QueryWrapper<ArticlesEntity> queryWrapper = new QueryWrapper<ArticlesEntity>().eq("article_title", vo.getArticleTitle());
        ArticlesEntity article = this.baseMapper.selectOne(queryWrapper);
        if (!StringUtils.isEmpty(article)) {
            return R.error(ArticlesConstrant.ARTICLE_ALEARDY_EXIST);
        }
        vo.setArticleDate(new Date());
        vo.setArticleLikeCount(0L);
        vo.setArticleViews(0L);
        vo.setArticleCommentCount(0L);
        vo.setIsDelete(0);
        ArticlesEntity articlesEntity = new ArticlesEntity();
        BeanUtils.copyProperties(vo, articlesEntity);
        List<LabelsEntity> labelsEntities = vo.getLabelNames().stream().map(item -> {
            LabelsEntity labelsEntity = new LabelsEntity();
            labelsEntity.setLabelName(item);
            //设置标签属于哪个用户
            labelsEntity.setUserId(articlesEntity.getUserId());
            return labelsEntity;
        }).collect(Collectors.toList());
        //插入文章
        int count = this.baseMapper.insert(articlesEntity);
        //插入标签
        boolean batch = labelsService.saveBatch(labelsEntities);
        //插入文章和标签的关系类
        List<SetArtitleLabelEntity> collect = labelsEntities.stream().map(item -> {
            SetArtitleLabelEntity setArtitleLabelEntity = new SetArtitleLabelEntity();
            setArtitleLabelEntity.setArticleId(articlesEntity.getArticleId());
            setArtitleLabelEntity.setLabelId(item.getLabelId());
            return setArtitleLabelEntity;
        }).collect(Collectors.toList());
        boolean saveBatch = setArtitleLabelService.saveBatch(collect);
        //插入文章和分类的关系表
        List<Long> sortIds = vo.getSortIds();
        SetArtitleSortEntity setArtitleSortEntity = new SetArtitleSortEntity();
        setArtitleSortEntity.setArticleId(articlesEntity.getArticleId());
        setArtitleSortEntity.setSortId(sortIds.get(sortIds.size()-1));
        int insert = setArtitleSortDao.insert(setArtitleSortEntity);
        if (!batch || count < 1 || !saveBatch || insert<1) {
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        }
        return R.ok();
    }

    @Transactional
    @Override
    public R updateArticles(ArticlesVo vo) {
        ArticlesEntity articleEnty = this.baseMapper.selectById(vo.getArticleId());
        BeanUtils.copyProperties(vo, articleEnty);
        articleEnty.setArticleDate(new Date());
        //修改文章
        int i = this.baseMapper.updateById(articleEnty);
        //先查出当前文章对应得标签id
        List<Long> labelIds = setArtitleLabelService.getLabelIds(articleEnty.getArticleId());
        //删除文章对应得所有标签
        boolean article_id = setArtitleLabelService.remove(new QueryWrapper<SetArtitleLabelEntity>().eq("article_id", articleEnty.getArticleId()));
        //再删除标签
        boolean label_id = true;
        if (labelIds != null && labelIds.size() > 0) {
            label_id = labelsService.remove(new QueryWrapper<LabelsEntity>().in("label_id", labelIds));
        }
        //标签添加
        List<LabelsEntity> labelsEntities = vo.getLabelNames().stream().map(item -> {
            LabelsEntity labelsEntity = new LabelsEntity();
            labelsEntity.setLabelName(item);
            //设置标签属于哪个用户
            labelsEntity.setUserId(articleEnty.getUserId());
            return labelsEntity;
        }).collect(Collectors.toList());
        boolean batch = labelsService.saveBatch(labelsEntities);
        //文章对应标签添加
        List<SetArtitleLabelEntity> collect = labelsEntities.stream().map(item -> {
            SetArtitleLabelEntity setArtitleLabelEntity = new SetArtitleLabelEntity();
            setArtitleLabelEntity.setArticleId(articleEnty.getArticleId());
            setArtitleLabelEntity.setLabelId(item.getLabelId());
            return setArtitleLabelEntity;
        }).collect(Collectors.toList());
        boolean saveBatch = setArtitleLabelService.saveBatch(collect);
        //修改文章的分类
        SetArtitleSortEntity setArtitleSortEntity = setArtitleSortDao.selectOne(new QueryWrapper<SetArtitleSortEntity>().eq("article_id", articleEnty.getArticleId()));
        if(StringUtils.isEmpty(setArtitleSortEntity)){
            SetArtitleSortEntity setArtitleSortEntity1 = new SetArtitleSortEntity();
            setArtitleSortEntity1.setArticleId(articleEnty.getArticleId());
            setArtitleSortEntity1.setSortId(vo.getSortIds().get(vo.getSortIds().size()-1));
            setArtitleSortDao.insert(setArtitleSortEntity1);
        }else{
            setArtitleSortEntity.setSortId(vo.getSortIds().get(vo.getSortIds().size()-1));
            setArtitleSortDao.updateById(setArtitleSortEntity);
        }
        //删除标签和Ids
        if (i < 1 || !article_id || !label_id || !batch || !saveBatch) {
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        }
        return R.ok();
    }

    @Override
    public R delete(Long articleId) {
        int i = this.baseMapper.deleteById(articleId);
        if (i < 1) {
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        } else {
            return R.ok();
        }
    }


    //每一个需要缓存的数据都要指定放在哪个名字的缓存【缓存的分区,按照业务类型分】
    //默认行为自动生成SimpleKey:[]自动生成的key值
    //缓存的值默认使用jdk序列机制,序列化后保存到redis
    //默认过期时间为永不过期
//    @Cacheable(value = {"articles"}, key = "#root.methodName+'::'+#articleId",sync = true)//代表当前的结果需要缓存,如果缓存中有,方法都不调用,没有就调用方法
    @Override
    public ArticlesEntity findArticleById(Long articleId) {
        //根据id查出当前文章
        ArticlesEntity articlesEntity = this.baseMapper.selectById(articleId);
        //更新当前文章的阅读量
        articlesEntity.setArticleViews(articlesEntity.getArticleViews()+1);
        //更新阅读量
        this.baseMapper.updateById(articlesEntity);
        //查出文章对应的标签
        List<Long> labelIds = setArtitleLabelService.getLabelIds(articleId);
        if (labelIds != null && labelIds.size() > 0) {
            List<LabelsEntity> labelsEntities = labelsDao.selectList(new QueryWrapper<LabelsEntity>().in("label_id", labelIds));
            articlesEntity.setLabelsEntityList(labelsEntities);
        }
        //找出文章对应的分类(分类是一个数组)
        SetArtitleSortEntity setArtitleSortEntity = setArtitleSortDao.selectOne(new QueryWrapper<SetArtitleSortEntity>().eq("article_id", articleId));
        if(!StringUtils.isEmpty(setArtitleSortEntity)){
            Long sortId = setArtitleSortEntity.getSortId();
            SortsEntity sortsEntity = sortsDao.selectById(sortId);
            List<Long> list = new ArrayList<>();
            list.add(sortsEntity.getSortId());
            list = parentCatagoryTree(list,sortsEntity.getParentSortId());
            Collections.reverse(list);
            articlesEntity.setSortIds(list);
            //查询出当前分类的名字
            SortsEntity entity = sortsDao.selectById(sortId);
            articlesEntity.setSortName(entity.getSortName());
        }
        return articlesEntity;
    }

    private List<Long> parentCatagoryTree(List<Long> list,Long parentSortId){
        if(parentSortId != 0){
            SortsEntity sortsEntity = sortsDao.selectById(parentSortId);
            list.add(sortsEntity.getSortId());
            parentCatagoryTree(list,sortsEntity.getParentSortId());
        }
        return list;
    }

    @Transactional
    @Override
    public R deleteArticle(Long articleId) {
        //TODO 是否删除标签
        //删除文章对应的所有标签
        boolean article_id = setArtitleLabelService.remove(new QueryWrapper<SetArtitleLabelEntity>().eq("article_id", articleId));
        //删除文章
        int i = this.baseMapper.deleteById(articleId);
        //删除文章对应的分类
        int delete = setArtitleSortDao.delete(new QueryWrapper<SetArtitleSortEntity>().eq("article_id", articleId));
        if(!article_id || i<1 || delete<1){
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        }
        return R.ok();
    }

    @Override
    public Set<String> getTimeList(String userId) {
        QueryWrapper<ArticlesEntity> queryWrapper = new QueryWrapper<ArticlesEntity>();
        if(userId != null){
            queryWrapper.eq("user_id",userId);
        }
        List<ArticlesEntity> articlesEntities = this.baseMapper.selectList(queryWrapper);
        Set<String> collect = articlesEntities.stream().map(item -> {
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
            String format = ft.format(item.getArticleDate());
            return format;
        }).collect(Collectors.toSet());
        return collect;
    }

    @Override
    public PageUtils selectListByTime(Map<String, Object> params, String time) {
        QueryWrapper<ArticlesEntity> queryWrapper = new QueryWrapper<ArticlesEntity>();
        queryWrapper.like("article_date",time);
        //设置排序字段
        params.put(Constant.ORDER_FIELD, "article_date");
        //设置升序
        params.put(Constant.ORDER, "asc");
        Object userId = params.get("userId");
        if(!StringUtils.isEmpty(userId)){
            queryWrapper.eq("user_id",params.get("userId"));
        }
        IPage<ArticlesEntity> page = this.page(
                new Query<ArticlesEntity>().getPage(params),
                queryWrapper
        );
        List<ArticlesEntity> records = page.getRecords();
        List<ArticlesEntity> articlesEntities = records.stream().map(item -> {
            //根据id查出所有的标签
            List<Long> labelIds = setArtitleLabelService.getLabelIds(item.getArticleId());
            List<LabelsEntity> labelsEntities = labelsDao.selectList(new QueryWrapper<LabelsEntity>().in("label_id", labelIds));
            item.setLabelsEntityList(labelsEntities);
            //添加分类名称
            SetArtitleSortEntity setArtitleSortEntity = setArtitleSortDao.selectOne(new QueryWrapper<SetArtitleSortEntity>().eq("article_id", item.getArticleId()));
            SortsEntity sortsEntity = sortsDao.selectById(setArtitleSortEntity.getSortId());
            item.setSortName(sortsEntity.getSortName());
            return item;
        }).collect(Collectors.toList());
        page.setRecords(articlesEntities);
        return new PageUtils(page);
    }

}