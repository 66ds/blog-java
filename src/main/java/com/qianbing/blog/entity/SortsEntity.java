package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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

	@TableId
	private Long sortId;

	private String sortName;

	private String sortAlias;

	private String sortDescription;

	private Long userId;

	private Long parentSortId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date sortTime;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private List<SortsEntity> children;

}
