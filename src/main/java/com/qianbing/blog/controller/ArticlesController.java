package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.ArticlesEntity;
import com.qianbing.blog.service.ArticlesService;
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
 * @date 2020-11-10 15:44:24
 */
@RestController
@RequestMapping("/api/v1/pri/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("${moduleName}:articles:list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = articlesService.queryPage(params);
        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{articleId}")
    public R info(@PathVariable("articleId") Long articleId){
		ArticlesEntity articles = articlesService.getById(articleId);

        return R.ok().put("data", articles);
    }

    /**
     * 删除单篇文章
     */
    @RequestMapping("/delete/{articleId}")
    public R delete(@PathVariable("articleId") Long articleId){
        return articlesService.delete(articleId);
    }

    /**
     * 保存
     */
    @RequestMapping("/add")
    //@RequiresPermissions("${moduleName}:articles:save")
    public R save(@RequestBody ArticlesEntity articles, HttpServletRequest request){
        //获取发表用户id
        Integer id = (Integer) request.getAttribute("id");
        articles.setUserId(id.longValue());
        return articlesService.saveArticles(articles);
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:articles:update")
    public R update(@RequestBody ArticlesEntity articles){
		return articlesService.updateArticles(articles);
    }

    /**
     * 删除
     */
    @RequestMapping("/batch")
    public R delete(@RequestParam(value = "articleIds") Long[] articleIds){
		articlesService.removeByIds(Arrays.asList(articleIds));
        return R.ok();
    }
}
