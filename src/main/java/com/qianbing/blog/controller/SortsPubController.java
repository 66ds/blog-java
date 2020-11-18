package com.qianbing.blog.controller;

import com.qianbing.blog.entity.SortsEntity;
import com.qianbing.blog.service.SortsService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:12:50
 */
@RestController
@RequestMapping("/api/v1/pub/sorts")
public class SortsPubController {

    @Autowired
    private SortsService sortsService;

    /**
     * 列表(查询所有只有根)
     */
    @RequestMapping("/list")
    public R list(){
        List<SortsEntity> list = sortsService.selectList();
        return R.ok().put("data", list);
    }

    /**
     * 查看某个分类下的所有文章(全部和某个用户)
     */
    @RequestMapping("/list/{sortId}")
    public R selectlistBySortId(@PathVariable("sortId") Long sortId,@RequestBody Map<String,Object> map){
        PageUtils pageUtils = sortsService.selectlistBySortId(sortId,map);
        return R.ok().put("data", pageUtils);
    }
}
