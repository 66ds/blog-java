package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.LikeCommentUserEntity;
import com.qianbing.blog.service.LikeCommentUserService;
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
 * @date 2020-11-24 20:58:37
 */
@RestController
@RequestMapping("/api/v1/pri/likecommentuser")
public class LikeCommentUserController {
    @Autowired
    private LikeCommentUserService likeCommentUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:likecommentuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = likeCommentUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{likeId}")
    //@RequiresPermissions("coupon:likecommentuser:info")
    public R info(@PathVariable("likeId") Long likeId){
		LikeCommentUserEntity likeCommentUser = likeCommentUserService.getById(likeId);

        return R.ok().put("likeCommentUser", likeCommentUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:likecommentuser:save")
    public R save(@RequestBody LikeCommentUserEntity likeCommentUser){
		likeCommentUserService.save(likeCommentUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:likecommentuser:update")
    public R update(@RequestBody LikeCommentUserEntity likeCommentUser){
		likeCommentUserService.updateById(likeCommentUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:likecommentuser:delete")
    public R delete(@RequestBody Long[] likeIds){
		likeCommentUserService.removeByIds(Arrays.asList(likeIds));

        return R.ok();
    }

    /**
     * 获取用户对某篇文章的所有评论
     * @param articleId
     * @param request
     * @return
     */
    @RequestMapping("/list/{articleId}")
    public R selectList(@PathVariable("articleId") Long articleId, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        return likeCommentUserService.selectList(articleId,userId);
    }

    /**
     * 用户点赞评论
     * @param commentId
     * @param request
     * @return
     */
    @RequestMapping("/like/{commentId}")
    public R likeArticle(@PathVariable("commentId") Long commentId, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        return likeCommentUserService.likeComment(commentId,userId);
    }

}
