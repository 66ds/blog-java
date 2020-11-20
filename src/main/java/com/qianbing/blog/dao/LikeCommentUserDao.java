package com.qianbing.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.LikeCommentUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-20 09:18:33
 */
@Repository
@Mapper
public interface LikeCommentUserDao extends BaseMapper<LikeCommentUserEntity> {
	
}
