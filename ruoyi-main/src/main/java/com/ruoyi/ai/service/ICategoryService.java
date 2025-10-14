package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.ai.domain.MenuItem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-21
 */
public interface ICategoryService extends IService<Category> {

    List<Category> getAllCategoryList();

    List<MenuItem> getMenus();

    List<Category> selectCategoryList(Category category);

    boolean checkNameExsit(Category category);

    boolean deleteCategory(Long[] categoryIds);

    List<String> getAllCategories();

}
