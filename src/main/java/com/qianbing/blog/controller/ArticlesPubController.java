package com.qianbing.blog.controller;

import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.service.ArticlesService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-10 15:44:24
 */
@RestController
@RequestMapping("/api/v1/pub/articles")
public class ArticlesPubController {

    @Autowired
    private ArticlesService articlesService;

    /**
     * 列表(未登录）
     */
    @RequestMapping("/list")
    public R list(@RequestBody Map<String, Object> params) {
        PageUtils page = articlesService.queryPage(params);
        return R.ok().put("data", page);
    }

    /**
     * 查询单篇文章信息
     */
    @RequestMapping("/info/{articleId}")
    public R info(@PathVariable("articleId") Long articleId) {
        ArticlesEntity articles = articlesService.findArticleById(articleId);
        return R.ok().put("data", articles);
    }
}
