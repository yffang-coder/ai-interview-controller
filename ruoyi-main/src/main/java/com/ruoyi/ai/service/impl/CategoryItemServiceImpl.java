package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.domain.CategoryItem;
import com.ruoyi.ai.mapper.CategoryItemMapper;
import com.ruoyi.ai.service.ICategoryItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CategoryItemMapper categoryItemMapper;
    @Override
    public List<CategoryItem> getCategoryItemList(String category) {
        LambdaQueryWrapper<CategoryItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryItem::getCategory, category);
        queryWrapper.eq(CategoryItem::getDelFlag, 1);
        queryWrapper.select(CategoryItem::getText, CategoryItem::getValue);
        queryWrapper.orderByAsc(CategoryItem::getOrderNum);
        return list(queryWrapper);
    }

    @Override
    public List<CategoryItem> selectCategoryItemList(CategoryItem category) {
        return categoryItemMapper.selectCategoryItemList(category);
    }

    @Override
    public boolean checkNameExsit(CategoryItem category) {
        LambdaQueryWrapper<CategoryItem> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(CategoryItem::getDelFlag, 1);
        if(category.getId() != null){
            queryWrapper.ne(CategoryItem::getId, category.getId());
        }
        // 分别检查value和text是否已存在
        queryWrapper.and(wrapper ->
                wrapper.eq(CategoryItem::getValue, category.getValue())
                        .or()
                        .eq(CategoryItem::getText, category.getText())
        );
        return exists(queryWrapper);
    }

    @Override
    public Boolean deleteCategoryItem(Long[] categoryIds) {
        LambdaUpdateWrapper<CategoryItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(CategoryItem::getId, categoryIds);
        updateWrapper.set(CategoryItem::getDelFlag, 0);

        return update(updateWrapper);
    }
}
