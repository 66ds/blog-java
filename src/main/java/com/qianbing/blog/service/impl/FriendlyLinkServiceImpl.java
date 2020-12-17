package com.qianbing.blog.service.impl;

import com.qianbing.blog.constrant.FriendLinkConstrant;
import com.qianbing.blog.dao.FriendlyLinkDao;
import com.qianbing.blog.entity.FriendlyLinkEntity;
import com.qianbing.blog.service.FriendlyLinkService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.Query;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


@Service("friendlyLinkService")
public class FriendlyLinkServiceImpl extends ServiceImpl<FriendlyLinkDao, FriendlyLinkEntity> implements FriendlyLinkService {

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    JavaMailSender jms;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<FriendlyLinkEntity> queryWrapper = new QueryWrapper<FriendlyLinkEntity>();
        String content = (String) params.get("content");
        if(!StringUtils.isEmpty(content)){
            queryWrapper .and(wrapper -> wrapper.like("link_name",content).or().like("link_desc", content));
        }
        IPage<FriendlyLinkEntity> page = this.page(
                new Query<FriendlyLinkEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<FriendlyLinkEntity> selectList() {
        //查询已经审核通过的友链
        List<FriendlyLinkEntity> friendlyLinkEntities = this.baseMapper.selectList(new QueryWrapper<FriendlyLinkEntity>().eq("link_allow", 0));
        return friendlyLinkEntities;
    }

    @Override
    public R updateLinkInfo(FriendlyLinkEntity friendlyLink) {

        CompletableFuture<Long> linkInfoFuture = CompletableFuture.supplyAsync(() -> {
            //查询之前友链信息是否审核通过
            FriendlyLinkEntity friendlyLinkEntity = this.baseMapper.selectById(friendlyLink.getLinkId());
            return friendlyLinkEntity.getLinkAllow();
        }, executor);


        CompletableFuture<Void> emailFuure = linkInfoFuture.thenAcceptAsync((res) -> {
            //如果之前没审核,更新过后变为审核,则发送邮件给友链者
            System.out.println(res);
            System.out.println(friendlyLink.getLinkAllow());
            if(res.equals(0L) && friendlyLink.getLinkAllow().equals(1L)){
                //发送邮件
                //建立邮件消息
                SimpleMailMessage mainMessage = new SimpleMailMessage();
                //发送者
                mainMessage.setFrom("1532498760@qq.com");
                //接收者
                mainMessage.setTo(friendlyLink.getLinkEmail());
                //发送的标题
                mainMessage.setSubject("友链添加成功");
                //发送的内容
                mainMessage.setText("友链添加成功啦!请查看");
                jms.send(mainMessage);
            }
        }, executor);

        CompletableFuture<Void> updateFuture = CompletableFuture.runAsync(() -> {
            //修改友链信息
            this.baseMapper.updateById(friendlyLink);
        }, executor);

        try {
            CompletableFuture.allOf(emailFuure,updateFuture).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return R.ok(FriendLinkConstrant.LINK_UPDATE_SUCCESS);
    }

}