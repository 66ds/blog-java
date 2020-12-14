package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.StayMessageConstrant;
import com.qianbing.blog.dao.StayMessageDao;
import com.qianbing.blog.dao.UsersDao;
import com.qianbing.blog.entity.StayMessageEntity;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.StayMessageService;
import com.qianbing.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


@Service("stayMessageService")
public class StayMessageServiceImpl extends ServiceImpl<StayMessageDao, StayMessageEntity> implements StayMessageService {

    @Autowired
    private UsersDao usersDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        String content = (String) params.get("content");
        //查询父留言id为0的所有留言
        QueryWrapper<StayMessageEntity> queryWrapper = new QueryWrapper<StayMessageEntity>();
        if(!StringUtils.isEmpty(content)){
            queryWrapper.like("message_content",content);
        }
        queryWrapper.eq("parent_stay_id",0);
        //设置排序字段
        params.put(Constant.ORDER_FIELD, "message_stay_time");
        //设置降序
        params.put(Constant.ORDER, "desc");
        IPage<StayMessageEntity> page = this.page(
                new Query<StayMessageEntity>().getPage(params),
                queryWrapper
        );
        List<StayMessageEntity> stayMessageEntities = page.getRecords();
        stayMessageEntities = stayMessageEntities.stream().map(item -> {
            //查询父留言用户信息
            UsersEntity pEntity = usersDao.selectById(item.getStayUserId());
            item.setUserName(pEntity.getUserNickname());
            item.setUserImg(pEntity.getUserProfilePhoto());
            //设置子评论
            StayMessageEntity entity = this.baseMapper.selectOne(new QueryWrapper<StayMessageEntity>().eq("parent_stay_id", item.getStayId()));
            if(!StringUtils.isEmpty(entity)){
                //查询子评论用户信息(因为只有一条所以不递归了)
                UsersEntity cNtity = usersDao.selectById(entity.getStayUserId());
                entity.setUserName(cNtity.getUserNickname());
                entity.setUserImg(cNtity.getUserProfilePhoto());
            }
            item.setStayMessageEntity(entity);
            return item;
        }).collect(Collectors.toList());
        page.setRecords(stayMessageEntities);
        return new PageUtils(page);
    }

    @Override
    public R addStayMessage(StayMessageEntity stayMessage, HttpServletRequest request) {
        stayMessage.setMessageStayTime(new Date());
        //设置留言者的登录Ip
        String ipAddr = GetIpAddress.getIpAddr(request);
        stayMessage.setStayUserIp(ipAddr);
        int insert = this.baseMapper.insert(stayMessage);
        if(insert < 1){
            return R.error(StayMessageConstrant.STAY_MESSAGE_SERVER_ERROR);
        }
        return R.ok(StayMessageConstrant.STAY_MESSAGE_SUCCESS);
    }

}