package com.qianbing.blog.dao;
import com.qianbing.blog.entity.CommentsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-23 10:12:03
 */
@Mapper
public interface CommentsDao extends BaseMapper<CommentsEntity> {
	
}
