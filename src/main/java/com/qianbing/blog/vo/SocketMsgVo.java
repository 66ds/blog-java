package com.qianbing.blog.vo;

import lombok.Data;

/**
 * @author qianbing
 * @date 2019/10/26
 * @email 1532498760@qq.com
 * @description 消息实体
 */
@Data
public class SocketMsgVo {
    private String aisle;//房间号.
    private String msg;//消息
}