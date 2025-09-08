package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.domain.MenuItem;
import com.ruoyi.ai.mapper.CategoryMapper;
import com.ruoyi.ai.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-21
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategoryList() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getDelFlag, 1);
        queryWrapper.orderByAsc(Category::getOrderNum);
        queryWrapper.select(Category::getText, Category::getSrc,
                Category::getName, Category::getEnable);
        return list(queryWrapper);
    }

    @Override
    public List<MenuItem> getMenus() {
        return categoryMapper.getMenus().stream().filter(item -> item.getChildren() != null && !item.getChildren().isEmpty())
                .map(item -> {
                    List<MenuItem> children = item.getChildren();

                    // 创建一个新的元素添加到头部
                    MenuItem newElement = new MenuItem();
                    newElement.setValue("all");
                    newElement.setText("全部");

                    // 创建新的列表，将新元素放在头部
                    List<MenuItem> newChildren = new ArrayList<>();
                    newChildren.add(newElement);
                    newChildren.addAll(children);
                    item.setChildren(newChildren);

                    return item;
                }).collect(Collectors.toList());
    }
}
