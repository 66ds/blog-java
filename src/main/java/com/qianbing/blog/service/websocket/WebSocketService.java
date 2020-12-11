package com.qianbing.blog.service.websocket;


import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.qianbing.blog.dao.SecretMessageDao;
import com.qianbing.blog.entity.SecretMessageEntity;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.SecretMessageService;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.MessagesVo;
import com.qianbing.blog.vo.SocketMsgVo;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author qianbing
 * @date 2019/10/26
 * @email 1532498760@qq.com
 * @description websocket的具体实现类
 *  使用springboot的唯一区别是要@Component声明，而使用独立容器是由容器自己管理websocket的，
 *  但在springboot中连容器都是spring管理的。
 *  虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
 *  所以可以用一个静态set保存起来
 */
@ServerEndpoint(value = "/websocket/{receivedId}/{sendId}/{secretId}")
@Component
public class WebSocketService {
    private Integer receivedId;

    private Session session;

    private static SecretMessageService secretMessageService;

    private static UsersService usersService;

    @Autowired
    public void setSecretMessageService(SecretMessageService secretMessageService,UsersService usersService) {
        WebSocketService.secretMessageService = secretMessageService;
        WebSocketService.usersService = usersService;
    }


    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    //用来记录sessionId和该session进行绑定
    private static Map<String, Session> map = new HashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("receivedId") Integer receivedId, @PathParam("sendId") Integer sendId) {
        //拼接成session的唯一表标识
        String unique = sendId+","+receivedId;
        Map<String,Object> message=new HashMap<>();
        this.session = session;
        this.receivedId = receivedId;
        map.put(unique, session);
        webSocketSet.add(this);//加入set中
        message.put("type",0); //消息类型，0-连接成功，1-用户消息
        message.put("aisle",unique); //频道号
        this.session.getAsyncRemote().sendText(new Gson().toJson(message));
    }

    /**
     * 连接关闭调用的方法    
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message,@PathParam("secretId") Integer secretId) {
        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsgVo socketMsg;
        try {
            socketMsg = objectMapper.readValue(message, SocketMsgVo.class);
            //单聊.需要找到发送者和接受者.
            Session fromSession = map.get(socketMsg.getAisle());
            String[] split = socketMsg.getAisle().split(",");
            //取相反的唯一标识
            String aisleTwo = split[1]+","+split[0];
            Session toSession = map.get(aisleTwo);
            //不管对方在不在线,都要将数据保存到数据库
            SecretMessageEntity secretMessageEntity = new SecretMessageEntity();
            secretMessageEntity.setSendId((long) Integer.parseInt(split[0]));
            secretMessageEntity.setReceiveId((long) Integer.parseInt(split[1]));
            secretMessageEntity.setParentSecretId(secretId.longValue());
            secretMessageEntity.setCreateTime(new Date());
            secretMessageEntity.setMessageContent(socketMsg.getMsg());
            secretMessageEntity.setIsRead(0L);
            secretMessageService.save(secretMessageEntity);
            //发送给接受者.
            R r = usersService.getUserInfoById(Integer.parseInt(split[0]));
            R r1 = usersService.getUserInfoById(Integer.parseInt(split[1]));
            MessagesVo messagesVo = new MessagesVo();
            BeanUtils.copyProperties(secretMessageEntity,messagesVo);
            messagesVo.setSendName(r1.getData(new TypeReference<UsersEntity>(){},"data").getUserNickname());
            messagesVo.setSendImg(r1.getData(new TypeReference<UsersEntity>(){},"data").getUserProfilePhoto());
            messagesVo.setReceivedName(r.getData(new TypeReference<UsersEntity>(){},"data").getUserNickname());
            messagesVo.setReceivedImg(r.getData(new TypeReference<UsersEntity>(){},"data").getUserProfilePhoto());
            Map<String,Object> m=new HashMap<>();
            m.put("type",1);
            m.put("msg",messagesVo);
            //发送给发送者.
            fromSession.getAsyncRemote().sendText(new Gson().toJson(m));
            if (toSession != null) {
                //发送给接受者.
                toSession.getAsyncRemote().sendText(new Gson().toJson(m));
            } else {
                //无需操作
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用   
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

}