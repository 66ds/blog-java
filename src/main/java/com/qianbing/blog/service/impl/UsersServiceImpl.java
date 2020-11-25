package com.qianbing.blog.service.impl;


import com.qianbing.blog.constrant.SmsContrant;
import com.qianbing.blog.constrant.UserContrant;
import com.qianbing.blog.dao.ArticlesDao;
import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.exception.PhoneExistException;
import com.qianbing.blog.utils.GetIpAddress;
import com.qianbing.blog.utils.Query;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianbing.blog.dao.UsersDao;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.utils.jwt.JWTUtils;
import com.qianbing.blog.vo.CardVo;
import com.qianbing.blog.vo.LoginVo;
import com.qianbing.blog.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletRequest;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private ArticlesDao articlesDao;

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

    @Override
    public R login(LoginVo vo) {
        String phone = vo.getUserTelephoneNumber();
        UsersEntity usersEntity = this.baseMapper.selectOne(new QueryWrapper<UsersEntity>().eq("user_telephone_number", phone));
        if(usersEntity == null){
            //登录失败
            return R.error(UserContrant.LOGIN_ERROR);
        }else{
            String pass = usersEntity.getUserPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(vo.getUserPassword(), pass);
            if(matches){
                String token = JWTUtils.geneJsonWebToken(usersEntity);
                return R.ok().setData(token);
            }else {
                return R.error(UserContrant.LOGIN_ERROR);
            }
        }
    }

    @Override
    public R getUserInfoById(Integer userId) {
        UsersEntity usersEntity = this.baseMapper.selectById(userId);
        return R.ok().setData(usersEntity);
    }

    @Override
    public R selectCardInfo(Long userId) {
        //根据用户id查找所有的文章数
        UsersEntity usersEntity = this.baseMapper.selectById(userId);
        CardVo cardVo = new CardVo();
        cardVo.setUserId(usersEntity.getUserId());
        cardVo.setUserImg(usersEntity.getUserProfilePhoto());
        cardVo.setUserName(usersEntity.getUserNickname());
        List<ArticlesEntity> articlesEntities = articlesDao.selectList(new QueryWrapper<ArticlesEntity>().eq("user_id", userId));
        cardVo.setAllArticlesLikeNumber(articlesEntities.stream().mapToLong(item -> item.getArticleLikeCount()).sum());
        cardVo.setAllArticlesCommentsNumber(articlesEntities.stream().mapToLong(item -> item.getArticleCommentCount()).sum());
        cardVo.setAllArticleViewsNumber(articlesEntities.stream().mapToLong(item -> item.getArticleViews()).sum());
        cardVo.setAllArticlesNumber((long) articlesEntities.size());
        return R.ok().setData(cardVo);
    }

    private void checkPhone(String phone) {
        Integer count = this.baseMapper.selectCount(new QueryWrapper<UsersEntity>().eq("user_telephone_number", phone));
        if(count>0){
            throw new PhoneExistException();
        }
    }
}