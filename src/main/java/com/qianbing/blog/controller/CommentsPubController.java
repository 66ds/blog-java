package com.qianbing.blog.controller;

import com.qianbing.blog.entity.CommentsEntity;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.CommentsService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-23 10:12:03
 */
@RestController
@RequestMapping("/api/v1/pub/comments")
public class CommentsPubController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("${moduleName}:comments:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commentsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{commentId}")
    //@RequiresPermissions("${moduleName}:comments:info")
    public R info(@PathVariable("commentId") Long commentId){
		CommentsEntity comments = commentsService.getById(commentId);

        return R.ok().put("comments", comments);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:comments:save")
    public R save(@RequestBody CommentsEntity comments){
		commentsService.save(comments);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:comments:update")
    public R update(@RequestBody CommentsEntity comments){
		commentsService.updateById(comments);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:comments:delete")
    public R delete(@RequestBody Long[] commentIds){
		commentsService.removeByIds(Arrays.asList(commentIds));

        return R.ok();
    }

    /**
     * 获取文章对应的所有评论
     * @param articleId
     * @return
     */
    @RequestMapping("/list/{articleId}")
    public R selectlistInfo(@PathVariable("articleId") Long articleId,@RequestBody Map<String, Object> params){
        PageUtils list = commentsService.selectListInfo(articleId,params);
        return R.ok().put("data", list);
    }

    /**
     * 根据父commentId获取用户信息
     * @param parentCommentId
     * @return
     */
    @RequestMapping("/users/{parentCommentId}")
    public R selectUserInfo(@PathVariable("parentCommentId") Long parentCommentId){
      UsersEntity usersEntity = commentsService.selectUserInfo(parentCommentId);
      return R.ok().setData(usersEntity);
    }
}
