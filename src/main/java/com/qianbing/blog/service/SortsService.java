package com.qianbing.blog.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianbing.blog.entity.SortsEntity;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-12 16:12:50
 */
public interface SortsService extends IService<SortsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SortsEntity> findCatagorysByUserId(Integer userId);

    R saveSort(SortsEntity sorts);

    R updateSorts(SortsEntity sorts);

    R deleteSorts(Long sortId,Integer userId);

    List<SortsEntity> selectList(Long userId);

    PageUtils selectlistBySortId(Long sortId,Map<String,Object> map);
}

