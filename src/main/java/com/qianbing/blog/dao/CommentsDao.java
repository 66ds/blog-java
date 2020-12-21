package com.qianbing.blog.dao;
import com.qianbing.blog.entity.CommentsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.vo.NoReadCommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-23 10:12:03
 */
@Repository
@Mapper
public interface CommentsDao extends BaseMapper<CommentsEntity> {

    /**
     * 获取未读评论信息
     * @param userId
     * @return
     */
    public List<NoReadCommentEntity> getNoReadCommentInfo(@Param("userId") Long userId);
}
