package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:12:50
 */
@Data
@TableName("zj_sorts")
public class SortsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long sortId;
	/**
	 * $column.comments
	 */
	private String sortName;
	/**
	 * $column.comments
	 */
	private String sortAlias;
	/**
	 * $column.comments
	 */
	private String sortDescription;
	/**
	 * $column.comments
	 */
	private Long userId;
	/**
	 * $column.comments
	 */
	private Long parentSortId;

}
