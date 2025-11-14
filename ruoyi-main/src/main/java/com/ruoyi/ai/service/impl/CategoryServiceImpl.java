package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.ai.domain.Banner;
import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.domain.MenuItem;
import com.ruoyi.ai.mapper.CategoryMapper;
import com.ruoyi.ai.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private MinioUtil minioUtil;

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

    @Override
    public List<Category> selectCategoryList(Category category) {
        return categoryMapper.selectCategoryList(category);
    }

    @Override
    public boolean checkNameExsit(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if(category.getId() != null){
            //更新操作 排除自身
            queryWrapper.ne(Category::getId, category.getId());
        }
        queryWrapper.eq(Category::getName, category.getName());
        queryWrapper.eq(Category::getDelFlag, 1);
        return exists(queryWrapper);
    }

    @Override
    public boolean deleteCategory(Long[] categoryIds) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Category::getId, categoryIds);
        queryWrapper.select(Category::getSrc);
        List<Category> categoryList = list(queryWrapper);
        if (CollectionUtils.isEmpty(categoryList)){
            return true;
        }
        List<String> urls = categoryList.stream().map(Category::getSrc).toList();
        urls.forEach(minioUtil::deleteFile);

        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Category::getId, categoryIds);
        updateWrapper.set(Category::getDelFlag, 0);
        return update(updateWrapper);
    }

    @Override
    public List<String> getAllCategories() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getName);
        queryWrapper.eq(Category::getDelFlag, 1);
        queryWrapper.isNotNull(Category::getName);
        List<Category> list = list(queryWrapper);
        return list.stream()
                .filter(category -> category.getName() != null && !category.getName().trim().isEmpty())
                .map(Category::getName).collect(Collectors.toList());
    }
}
