package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.service.ICategoryService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-21
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    ICategoryService categoryService;

    @PreAuthorize("@ss.hasPermi('ai:category:search')")
    @GetMapping("/list")
    public TableDataInfo list(Category category) {
        //PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        //开启分页拦截
        startPage();
        List<Category> list = categoryService.selectCategoryList(category);
        //将数据封装为分页数据结构
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('ai:category:add')")
    @Log(title = "category管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Category category) {
        if (categoryService.checkNameExsit(category)) {
            return error("新增category'" + category.getName() + "'失败，名称已存在");
        }
        return toAjax(categoryService.save(category));

    }

    @PreAuthorize("@ss.hasPermi('ai:category:remove')")
    @Log(title = "category管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds) {
        return toAjax(categoryService.deleteCategory(categoryIds));
    }


    @PreAuthorize("@ss.hasPermi('ai:category:update')")
    @Log(title = "category管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Category category) {
        if (categoryService.checkNameExsit(category)) {
            return error("编辑category'" + category.getName() + "'失败，名称已存在");
        }
        return toAjax(categoryService.updateById(category));
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:category:search')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable Integer categoryId) {
        return success(categoryService.getById(categoryId));
    }
}
