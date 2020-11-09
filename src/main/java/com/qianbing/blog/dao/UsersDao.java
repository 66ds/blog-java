package com.qianbing.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.UsersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-09 09:12:57
 */
@Mapper
@Repository
public interface UsersDao extends BaseMapper<UsersEntity> {
	
}
