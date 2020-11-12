package com.qianbing.blog.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.SortsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:12:50
 */
@Mapper
@Repository
public interface SortsDao extends BaseMapper<SortsEntity> {
	
}
