package com.qianbing.blog.dao;

import com.qianbing.blog.entity.UserAttentionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.vo.WhoAttentionMeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-27 22:57:08
 */
@Repository
@Mapper
public interface UserAttentionDao extends BaseMapper<UserAttentionEntity> {

    /**
     * 获取谁关注我信息
     * @param userId
     * @return
     */
    public List<WhoAttentionMeEntity> getWhoAttentionMeInfo(@Param("userId") Long userId);

    /**
     * 清空信息(单个和多个)
     * @param attentionIds
     */
    void deleteWhoAttentionMeInfo(@Param("list") List<Long> attentionIds);
}
