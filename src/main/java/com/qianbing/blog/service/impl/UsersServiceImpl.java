package com.qianbing.blog.service.impl;


import com.qianbing.blog.exception.PhoneExistException;
import com.qianbing.blog.utils.GetIpAddress;
import com.qianbing.blog.utils.Query;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianbing.blog.dao.UsersDao;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletRequest;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersEntity> page = this.page(
                new Query<UsersEntity>().getPage(params),
                new QueryWrapper<UsersEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R register(RegisterVo registerVo, HttpServletRequest httpServletRequest) {
        //判断之前有没有注册过
        UsersEntity usersEntity = new UsersEntity();
        //设置默认等级
        //检测用户名和手机号的唯一性
        checkPhone(registerVo.getUserTelephoneNumber());
        //TODO 用户名暂时用手机
        usersEntity.setUserName(registerVo.getUserTelephoneNumber()+"");
        //设置登录的ip地址
        usersEntity.setUserIp(GetIpAddress.getIpAddr(httpServletRequest));
        //设置密码(密码进行加密存储)
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(registerVo.getUserPassword());
        usersEntity.setUserPassword(encode);
        //设置手机号
        usersEntity.setUserTelephoneNumber(registerVo.getUserTelephoneNumber());
        //设置昵称
        usersEntity.setUserNickname("66ds");
        //设置冻结
        usersEntity.setUserLock(0);
        usersEntity.setUserFreeze(0);
        //TODO其他默认信息
        this.baseMapper.insert(usersEntity);
        return R.ok();
    }

    private void checkPhone(String phone) {
        Integer count = this.baseMapper.selectCount(new QueryWrapper<UsersEntity>().eq("user_telephone_number", phone));
        if(count>0){
            throw new PhoneExistException();
        }
    }
}