package com.qianbing.blog.dao;

import com.qianbing.blog.entity.StayMessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-13 20:46:28
 */
@Repository
@Mapper
public interface StayMessageDao extends BaseMapper<StayMessageEntity> {
	
}
