package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.qianbing.blog.entity.LikeArticleUserEntity;
import com.qianbing.blog.service.LikeArticleUserService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-20 09:51:18
 */
@RestController
@RequestMapping("/api/v1/pri/likearticleuser")
public class LikeArticleUserPriController {
    @Autowired
    private LikeArticleUserService likeArticleUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("${moduleName}:likearticleuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = likeArticleUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{likeId}")
    //@RequiresPermissions("${moduleName}:likearticleuser:info")
    public R info(@PathVariable("likeId") Long likeId){
		LikeArticleUserEntity likeArticleUser = likeArticleUserService.getById(likeId);

        return R.ok().put("likeArticleUser", likeArticleUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:likearticleuser:save")
    public R save(@RequestBody LikeArticleUserEntity likeArticleUser){
		likeArticleUserService.save(likeArticleUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:likearticleuser:update")
    public R update(@RequestBody LikeArticleUserEntity likeArticleUser){
		likeArticleUserService.updateById(likeArticleUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:likearticleuser:delete")
    public R delete(@RequestBody Long[] likeIds){
		likeArticleUserService.removeByIds(Arrays.asList(likeIds));

        return R.ok();
    }

    /**
     * 判断用户是否点赞了文章
     * @param articleId
     * @param request
     * @return
     */
    @RequestMapping("/list/{articleId}")
    public R selectList(@PathVariable("articleId") Long articleId, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        return likeArticleUserService.selectList(articleId,userId);
    }

    /**
     * 用户点赞文章
     * @param articleId
     * @param request
     * @return
     */
    @RequestMapping("/like/{articleId}")
    public R likeArticle(@PathVariable("articleId") Long articleId, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        return likeArticleUserService.likeArticle(articleId,userId);
    }

    /**
     * 获取谁点赞我的信息
     * @param request
     * @return
     */
    @RequestMapping("/get/who/dig")
    public R getWhoDigMeInfo(HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        return likeArticleUserService.getWhoDigMeInfo(id.longValue());
    }


    /**
     * 清空信息(单个和多个)
     * @param likeIds
     * @return
     */
    @RequestMapping("/delete/who/dig")
    public R deleteWhoDigMeInfo(@RequestBody List<Map<String,Object>> likeIds){
        return likeArticleUserService.deleteWhoDigMeInfo(likeIds);
    }
}
