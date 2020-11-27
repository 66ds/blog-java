package com.qianbing.blog.dao;

import com.qianbing.blog.entity.UserAttentionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
	
}
