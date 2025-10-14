package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.domain.CategoryItem;
import com.ruoyi.ai.service.ICategoryItemService;
import com.ruoyi.ai.service.ICategoryService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@RestController
@RequestMapping("/categoryItem")
public class CategoryItemController extends BaseController {

    @Autowired
    ICategoryItemService iCategoryItemService;

    @Autowired
    ICategoryService categoryService;

    @GetMapping("/getAllCategoryName")
    public List<String> getAllCategoryList() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize("@ss.hasPermi('ai:categoryItem:search')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryItem category) {
        //PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        //开启分页拦截
        startPage();
        List<CategoryItem> list = iCategoryItemService.selectCategoryItemList(category);
        //将数据封装为分页数据结构
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('ai:categoryItem:add')")
    @Log(title = "categoryItem 管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CategoryItem category) {
        if (iCategoryItemService.checkNameExsit(category)) {
            return error("新增category Item'" + category.getValue() + "'失败，名称已存在");
        }
        return toAjax(iCategoryItemService.save(category));

    }

    @PreAuthorize("@ss.hasPermi('ai:categoryItem:remove')")
    @Log(title = "categoryItem 管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds) {
        return toAjax(iCategoryItemService.deleteCategoryItem(categoryIds));
    }


    @PreAuthorize("@ss.hasPermi('ai:categoryItem:update')")
    @Log(title = "categoryItem 管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CategoryItem category) {
        if (iCategoryItemService.checkNameExsit(category)) {
            return error("编辑category '" + category.getValue() + "'失败，名称已存在");
        }
        return toAjax(iCategoryItemService.updateById(category));
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:categoryItem:search')")
    @GetMapping(value = "/{categoryItemId}")
    public AjaxResult getInfo(@PathVariable Integer categoryItemId) {
        return success(iCategoryItemService.getById(categoryItemId));
    }
}
