package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.CommentsEntity;
import com.qianbing.blog.service.CommentsService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import eu.bitwalker.useragentutils.UserAgent;
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
 * @date 2020-11-23 10:12:03
 */
@RestController
@RequestMapping("/api/v1/pri/comments")
public class CommentsPriController {
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
     * 点击评论
     * @param comments
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody CommentsEntity comments, HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String sys = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String chrome = userAgent.getBrowser().getName();
        Integer userId = (Integer) request.getAttribute("id");
        comments.setUserId(userId.longValue());
        comments.setCommentChrome(chrome);
        comments.setCommentSys(sys);
		return commentsService.addComments(comments);
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



}
