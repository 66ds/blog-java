package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:12:50
 */
@Data
@TableName("zj_labels")
public class LabelsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long labelId;
	/**
	 * $column.comments
	 */
	private String labelName;
	/**
	 * $column.comments
	 */
	private String labelAlias;
	/**
	 * $column.comments
	 */
	private String labelDescription;
	/**
	 * $column.comments
	 */
	private Long userId;

}
