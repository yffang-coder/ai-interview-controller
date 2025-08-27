package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.domain.CategoryItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
public interface ICategoryItemService extends IService<CategoryItem> {


    List<CategoryItem> getCategoryItemList(String category);
}
