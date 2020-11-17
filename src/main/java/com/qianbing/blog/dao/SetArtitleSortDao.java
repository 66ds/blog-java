package com.qianbing.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.SetArtitleSortEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:13:08
 */
@Repository
@Mapper
public interface SetArtitleSortDao extends BaseMapper<SetArtitleSortEntity> {
	
}
