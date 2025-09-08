package com.ruoyi.ai.mapper;

import com.ruoyi.ai.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.ai.domain.MenuItem;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-21
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<MenuItem> getMenus();
}
