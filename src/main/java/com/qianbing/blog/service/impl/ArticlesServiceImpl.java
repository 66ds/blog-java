package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.ArticlesConstrant;
import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.dao.LabelsDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.entity.LabelsEntity;
import com.qianbing.blog.entity.SetArtitleLabelEntity;
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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
            //TODO 添加分类
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
        if (!batch || count < 1 || !saveBatch) {
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

    @Override
    public ArticlesEntity findArticleById(Long articleId) {
        //根据id查出当前文章
        ArticlesEntity articlesEntity = this.baseMapper.selectById(articleId);
        //查出文章对应的标签
        List<Long> labelIds = setArtitleLabelService.getLabelIds(articleId);
        if (labelIds != null && labelIds.size() > 0) {
            List<LabelsEntity> labelsEntities = labelsDao.selectList(new QueryWrapper<LabelsEntity>().in("label_id", labelIds));
            articlesEntity.setLabelsEntityList(labelsEntities);
        }
        return articlesEntity;
    }

    @Transactional
    @Override
    public R deleteArticle(Long articleId) {
        //TODO 是否删除标签
        //删除文章对应的所有标签
        boolean article_id = setArtitleLabelService.remove(new QueryWrapper<SetArtitleLabelEntity>().eq("article_id", articleId));
        //删除文章
        int i = this.baseMapper.deleteById(articleId);
        if(!article_id || i<1){
            return R.error(ArticlesConstrant.ARTICLE_SERVER_ERROR);
        }
        return R.ok();
    }

}