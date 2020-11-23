package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.CommentsConstrant;
import com.qianbing.blog.dao.CommentsDao;
import com.qianbing.blog.entity.CommentsEntity;
import com.qianbing.blog.entity.SortsEntity;
import com.qianbing.blog.service.CommentsService;
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



@Service("commentsService")
public class CommentsServiceImpl extends ServiceImpl<CommentsDao, CommentsEntity> implements CommentsService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentsEntity> page = this.page(
                new Query<CommentsEntity>().getPage(params),
                new QueryWrapper<CommentsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CommentsEntity> selectListInfo(Long articleId) {
        //根据id查询出文章下的所有评论
        List<CommentsEntity> commentsEntities = this.baseMapper.selectList(new QueryWrapper<CommentsEntity>().eq("article_id", articleId));
        if(commentsEntities != null && commentsEntities.size()>0){
            List<CommentsEntity> collect = commentsEntities.stream().filter(commentsEntity ->
                    commentsEntity.getParentCommentId() == 0
            ).map(menu -> {
                menu.setChildren(getChildrenTree(menu, commentsEntities));
                return menu;
            }).sorted((men1, men2) ->
                    (int) (men2.getCommentDate().getTime() - men1.getCommentDate().getTime())
            ).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    @Override
    public R addComments(CommentsEntity comments) {
        comments.setCommentDate(new Date());
        comments.setCommentLikeCount(0L);
        int insert = this.baseMapper.insert(comments);
        if(insert < 1){
            return R.error(CommentsConstrant.COMMENTS_SERVER_ERROR);
        }
        return R.ok();
    }


    //查找父评论下的子评论
    private List<CommentsEntity> getChildrenTree(CommentsEntity commentsEntity, List<CommentsEntity> commentsEntities) {
        List<CommentsEntity> list = commentsEntities.stream().filter(commentsEntity1 ->
                commentsEntity1.getParentCommentId() == commentsEntity.getCommentId()
        ).map(menu -> {
            menu.setChildren(getChildrenTree(menu, commentsEntities));
            return menu;
        }).sorted((men1, men2) ->
                (int) (men2.getCommentDate().getTime() - men1.getCommentDate().getTime())
        ).collect(Collectors.toList());
        return list;
    }
}