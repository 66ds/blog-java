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
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articlesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{articleId}")
    //@RequiresPermissions("${moduleName}:articles:info")
    public R info(@PathVariable("articleId") Long articleId){
		ArticlesEntity articles = articlesService.getById(articleId);

        return R.ok().put("articles", articles);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:articles:save")
    public R save(@RequestBody ArticlesEntity articles){
		articlesService.save(articles);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:articles:update")
    public R update(@RequestBody ArticlesEntity articles){
		articlesService.updateById(articles);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:articles:delete")
    public R delete(@RequestBody Long[] articleIds){
		articlesService.removeByIds(Arrays.asList(articleIds));

        return R.ok();
    }

}
