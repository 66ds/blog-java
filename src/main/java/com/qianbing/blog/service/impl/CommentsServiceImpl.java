package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.CommentsConstrant;
import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.dao.CommentsDao;
import com.qianbing.blog.dao.UsersDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.entity.CommentsEntity;
import com.qianbing.blog.entity.SortsEntity;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.CommentsService;
import com.qianbing.blog.utils.Constant;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;


@Service("commentsService")
public class CommentsServiceImpl extends ServiceImpl<CommentsDao, CommentsEntity> implements CommentsService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private ArticlesDao articlesDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentsEntity> page = this.page(
                new Query<CommentsEntity>().getPage(params),
                new QueryWrapper<CommentsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils selectListInfo(Long articleId,Map<String, Object> params) {
        //根据id查询出文章下的所有评论
        QueryWrapper<CommentsEntity> queryWrapper = new QueryWrapper<CommentsEntity>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("parent_comment_id",0);
        //设置排序字段
        params.put(Constant.ORDER_FIELD, "comment_date");
        //设置降序
        params.put(Constant.ORDER, "desc");
        IPage<CommentsEntity> page = this.page(
                new Query<CommentsEntity>().getPage(params),
                queryWrapper
        );
        List<CommentsEntity> commentsEntities = page.getRecords();
        List<CommentsEntity> commentsEntityList = this.baseMapper.selectList(new QueryWrapper<CommentsEntity>().eq("article_id",articleId));
        if(commentsEntities != null && commentsEntities.size()>0){
            List<CommentsEntity> collect = commentsEntities.stream().map(menu -> {
                //设置用户的信息
                Long userId = menu.getUserId();
                UsersEntity usersEntity = usersDao.selectById(userId);
                menu.setUsersEntity(usersEntity);
                menu.setChildren(getChildrenTree(menu, commentsEntityList));
                return menu;
            }).sorted((men1, men2) ->
                    (int) (men2.getCommentDate().getTime() - men1.getCommentDate().getTime())
            ).collect(Collectors.toList());
            page.setRecords(collect);
        }
        return  new PageUtils(page);
    }

    @Transactional
    @Override
    public R addComments(CommentsEntity comments) {
        comments.setCommentDate(new Date());
        comments.setCommentLikeCount(0L);
        //默认未读
        comments.setIsRead(0L);
        //添加评论并且文章的评论数加1
        int insert = this.baseMapper.insert(comments);
        ArticlesEntity articlesEntity = articlesDao.selectById(comments.getArticleId());
        articlesEntity.setArticleCommentCount(articlesEntity.getArticleCommentCount()+1);
        int number = articlesDao.updateById(articlesEntity);
        if(insert < 1 || number < 1){
            return R.error(CommentsConstrant.COMMENTS_SERVER_ERROR);
        }
        return R.ok();
    }

    @Override
    public UsersEntity selectUserInfo(Long parentCommentId) {
        CommentsEntity commentsEntity = this.baseMapper.selectById(parentCommentId);
        UsersEntity usersEntity = usersDao.selectById(commentsEntity.getUserId());
        return usersEntity;
    }


    //查找父评论下的子评论
    private List<CommentsEntity> getChildrenTree(CommentsEntity commentsEntity, List<CommentsEntity> commentsEntityList) {
        List<CommentsEntity> list = commentsEntityList.stream().filter(commentsEntity1 ->
                commentsEntity1.getParentCommentId() == commentsEntity.getCommentId()
        ).map(menu -> {
            Long userId = menu.getUserId();
            UsersEntity usersEntity = usersDao.selectById(userId);
            menu.setUsersEntity(usersEntity);
            menu.setParentUsersEntity(selectUserInfo(menu.getParentCommentId()));
            menu.setChildren(getChildrenTree(menu, commentsEntityList));
            return menu;
        }).sorted((men1, men2) ->
                (int) (men1.getCommentDate().getTime() - men2.getCommentDate().getTime())
        ).collect(Collectors.toList());
        return list;
    }
}