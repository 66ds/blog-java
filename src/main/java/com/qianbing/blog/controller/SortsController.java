package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.SortsEntity;
import com.qianbing.blog.service.SortsService;
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
 * @date 2020-11-12 16:12:50
 */
@RestController
@RequestMapping("/api/v1/pri/sorts")
public class SortsController {
    @Autowired
    private SortsService sortsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("${moduleName}:sorts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sortsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{sortId}")
    //@RequiresPermissions("${moduleName}:sorts:info")
    public R info(@PathVariable("sortId") Long sortId){
		SortsEntity sorts = sortsService.getById(sortId);

        return R.ok().put("sorts", sorts);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:sorts:save")
    public R save(@RequestBody SortsEntity sorts){
		sortsService.save(sorts);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:sorts:update")
    public R update(@RequestBody SortsEntity sorts){
		sortsService.updateById(sorts);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:sorts:delete")
    public R delete(@RequestBody Long[] sortIds){
		sortsService.removeByIds(Arrays.asList(sortIds));

        return R.ok();
    }

}
