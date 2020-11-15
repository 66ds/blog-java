package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.List;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
    public R list(@RequestBody Map<String, Object> params,HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        params.put("userId",userId);
        PageUtils page = sortsService.queryPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{sortId}")
    //@RequiresPermissions("${moduleName}:sorts:info")
    public R info(@PathVariable("sortId") Long sortId){
		SortsEntity sorts = sortsService.getById(sortId);

        return R.ok().put("data", sorts);
    }

    /**
     * 保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody SortsEntity sorts,HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        sorts.setUserId(id.longValue());
		return sortsService.saveSort(sorts);
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SortsEntity sorts){
		return sortsService.updateSorts(sorts);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{sortId}")
    //@RequiresPermissions("${moduleName}:sorts:delete")
    public R delete(@PathVariable("sortId") Long sortId,HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        return sortsService.deleteSorts(sortId,userId);
    }

    /**
     * 查找该用户下所有的分类
     */
    @RequestMapping("/catagorys")
    public R findCatagorysByUserId(HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        List<SortsEntity> list = sortsService.findCatagorysByUserId(id);
        return R.ok().setData(list);
    }

}
