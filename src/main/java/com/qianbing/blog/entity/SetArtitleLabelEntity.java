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
 * @date 2020-11-12 16:13:08
 */
@Data
@TableName("zj_set_artitle_label")
public class SetArtitleLabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	private Long articleId;
	/**
	 * $column.comments
	 */
	private Long labelId;

	@TableId
	private Long id;

}
