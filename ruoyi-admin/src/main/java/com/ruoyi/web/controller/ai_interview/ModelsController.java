package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.ai.domain.Models;
import com.ruoyi.ai.domain.Models;
import com.ruoyi.ai.service.IModelsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@RestController
@RequestMapping("/models")
public class ModelsController extends BaseController {
    @Autowired
    IModelsService iModelsService;

    @PreAuthorize("@ss.hasPermi('ai:model:search')")
    @GetMapping("/list")
    public TableDataInfo list(Models models) {
        //PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        //开启分页拦截
        startPage();
        List<Models> list = iModelsService.selectModelsList(models);
        //将数据封装为分页数据结构
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('ai:model:add')")
    @Log(title = "Models管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Models models) {
        if (iModelsService.checkNameExsit(models)) {
            return error("新增Models'" + models.getName() + "'失败，名称已存在");
        }
        return toAjax(iModelsService.save(models));

    }

    @PreAuthorize("@ss.hasPermi('ai:model:remove')")
    @Log(title = "Models管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ModelsIds}")
    public AjaxResult remove(@PathVariable Long[] ModelsIds) {
        return toAjax(iModelsService.deleteModels(ModelsIds));
    }


    @PreAuthorize("@ss.hasPermi('ai:model:update')")
    @Log(title = "Models管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Models Models) {
        if (iModelsService.checkNameExsit(Models)) {
            return error("编辑Models'" + Models.getName() + "'失败，名称已存在");
        }
        return toAjax(iModelsService.updateById(Models));
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:model:search')")
    @GetMapping(value = "/{modelsId}")
    public AjaxResult getInfo(@PathVariable Integer modelsId) {
        return success(iModelsService.getById(modelsId));
    }
}
