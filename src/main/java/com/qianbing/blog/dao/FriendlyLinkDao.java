package com.qianbing.blog.dao;

import com.qianbing.blog.entity.FriendlyLinkEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-16 20:43:29
 */
@Repository
@Mapper
public interface FriendlyLinkDao extends BaseMapper<FriendlyLinkEntity> {
	
}
