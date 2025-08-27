package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.domain.CategoryItem;
import com.ruoyi.ai.mapper.CategoryItemMapper;
import com.ruoyi.ai.service.ICategoryItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@Service
public class CategoryItemServiceImpl extends ServiceImpl<CategoryItemMapper, CategoryItem> implements ICategoryItemService {


    @Override
    public List<CategoryItem> getCategoryItemList(String category) {
        LambdaQueryWrapper<CategoryItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryItem::getCategory, category);
        queryWrapper.eq(CategoryItem::getDelFlag, 1);
        queryWrapper.select(CategoryItem::getText, CategoryItem::getValue);
        queryWrapper.orderByAsc(CategoryItem::getOrderNum);
        return list(queryWrapper);
    }
}
