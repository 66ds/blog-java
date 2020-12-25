package com.qianbing.blog.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.LikeArticleUserEntity;
import com.qianbing.blog.entity.vo.WhoDigMeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-20 09:51:18
 */
@Mapper
public interface LikeArticleUserDao extends BaseMapper<LikeArticleUserEntity> {

    /**
     * 获取谁点赞我的信息
     * @param userId
     * @return
     */
    public List<WhoDigMeEntity> getWhoDigMeInfo(@Param("userId") Long userId);

    void deleteWhoDigMeInfo(Integer alias);
}
