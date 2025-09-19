package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.ai.domain.Banner;
import com.ruoyi.ai.service.IBannerService;
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
 * 前端控制器
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@RestController
@RequestMapping("/banner")
public class BannerController extends BaseController {
    @Autowired
    IBannerService bannerService;

    @PreAuthorize("@ss.hasPermi('ai:banner:search')")
    @GetMapping("/list")
    public TableDataInfo list(Banner banner) {
        //PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        //开启分页拦截
        startPage();
        List<Banner> list = bannerService.selectBannerList(banner);
        //将数据封装为分页数据结构
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('ai:banner:add')")
    @Log(title = "banner管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Banner banner) {
        if (bannerService.checkTitleExsit(banner)) {
            return error("新增banner'" + banner.getTitle() + "'失败，名称已存在");
        }
        return toAjax(bannerService.save(banner));

    }

    @PreAuthorize("@ss.hasPermi('ai:banner:remove')")
    @Log(title = "banner管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bannerIds}")
    public AjaxResult remove(@PathVariable Long[] bannerids) {
        return toAjax(bannerService.deleteBanner(bannerids));
    }


    @PreAuthorize("@ss.hasPermi('ai:banner:update')")
    @Log(title = "banner管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Banner banner) {
        if (bannerService.checkTitleExsit(banner)) {
            return error("编辑banner'" + banner.getTitle() + "'失败，名称已存在");
        }
        return toAjax(bannerService.updateById(banner));
    }
}
