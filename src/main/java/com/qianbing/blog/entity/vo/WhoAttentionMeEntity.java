package com.qianbing.blog.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WhoAttentionMeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String attentionIds;

    private String userIds;

    private String userNames;

    private Date attentionDate;
}
