package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.SetArtitleSortEntity;
import com.qianbing.blog.service.SetArtitleSortService;
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
 * @date 2020-11-12 16:13:08
 */
@RestController
@RequestMapping("/api/v1/pri/setartitlesort")
public class SetArtitleSortController {
    @Autowired
    private SetArtitleSortService setArtitleSortService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("${moduleName}:setartitlesort:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = setArtitleSortService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{articleId}")
    //@RequiresPermissions("${moduleName}:setartitlesort:info")
    public R info(@PathVariable("articleId") Long articleId){
		SetArtitleSortEntity setArtitleSort = setArtitleSortService.getById(articleId);

        return R.ok().put("setArtitleSort", setArtitleSort);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:setartitlesort:save")
    public R save(@RequestBody SetArtitleSortEntity setArtitleSort){
		setArtitleSortService.save(setArtitleSort);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:setartitlesort:update")
    public R update(@RequestBody SetArtitleSortEntity setArtitleSort){
		setArtitleSortService.updateById(setArtitleSort);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:setartitlesort:delete")
    public R delete(@RequestBody Long[] articleIds){
		setArtitleSortService.removeByIds(Arrays.asList(articleIds));

        return R.ok();
    }

}
