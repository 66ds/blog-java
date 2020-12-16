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
 * @date 2020-12-16 20:43:29
 */
@Data
@TableName("zj_friendly_link")
public class FriendlyLinkEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@TableId
	private Long linkId;
	/**
	 * 链接名称
	 */
	private String linkName;
	/**
	 * 链接地址
	 */
	private String linkUrl;
	/**
	 * 链接邮箱
	 */
	private String linkEmail;
	/**
	 * 链接详细信息
	 */
	private String linkDesc;
	/**
	 * 链接创建时间
	 */
	private Date linkCreateTime;

	/**
	 * 链接是否通过
	 */
	private Long linkAllow;

}
