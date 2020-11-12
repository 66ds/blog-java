package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-10 15:44:24
 */
@Data
@TableName("zj_articles")
public class ArticlesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long articleId;

	private Long userId;

	private String articleTitle;

	private String articleContent;

	private String articleContentOrigin;

	private Long articleViews;

	private Long articleCommentCount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date articleDate;

	private Long articleLikeCount;

	private Integer articleType;

	private Integer articleUp;

	private Integer articleSupport;

	@TableLogic//实现逻辑删除
	private Integer isDelete;

	//对应多个标签
	@TableField(exist = false)
	private List<LabelsEntity> labelsEntityList;

}
