package com.qianbing.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbing.blog.entity.SecretMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-09 14:18:27
 */
@Repository
@Mapper
public interface SecretMessageDao extends BaseMapper<SecretMessageEntity> {

    List<SecretMessageEntity> selectMessagesList(@Param("sendId") Integer sendId,@Param("receiveId") Integer receiveId);
}
