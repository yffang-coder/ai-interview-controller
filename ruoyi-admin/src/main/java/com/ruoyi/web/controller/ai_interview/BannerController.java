package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.ai.domain.Banner;
import com.ruoyi.ai.service.IBannerService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.MinioUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
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

    @Autowired
    private MinioUtil minioUtil;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

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
    public AjaxResult add(@ModelAttribute Banner banner, @RequestParam("file") MultipartFile file) {
        if (bannerService.checkTitleExsit(banner)) {
            return error("新增banner'" + banner.getTitle() + "'失败，名称已存在");
        }
        String uploadFileUrl = "";
        if(Strings.isEmpty(banner.getUrl())){
            // 处理上传的文件
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = sdf.format(System.currentTimeMillis()) + fileExtension;
            uploadFileUrl = minioUtil.uploadFile(file, newFilename);
        }else {
            uploadFileUrl = banner.getUrl();
        }
        banner.setUrl(uploadFileUrl);
        banner.setImage(uploadFileUrl);
        return toAjax(bannerService.save(banner));

    }

    @PreAuthorize("@ss.hasPermi('ai:banner:remove')")
    @Log(title = "banner管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bannerIds}")
    public AjaxResult remove(@PathVariable Long[] bannerIds) {
        return toAjax(bannerService.deleteBanner(bannerIds));
    }


    @PreAuthorize("@ss.hasPermi('ai:banner:update')")
    @Log(title = "banner管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ModelAttribute Banner banner, @RequestParam(value = "file",required = false) MultipartFile file) {
        if (bannerService.checkTitleExsit(banner)) {
            return error("编辑banner'" + banner.getTitle() + "'失败，名称已存在");
        }
        String uploadFileUrl = banner.getUrl();
        if(Strings.isEmpty(uploadFileUrl) && file != null){
            // 删除旧的文件
            minioUtil.deleteFile(minioUtil.extractFileNameFromUrl(banner.getUrl()));
            // 处理上传的文件
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = sdf.format(System.currentTimeMillis()) + fileExtension;
            uploadFileUrl = minioUtil.uploadFile(file, newFilename);

        }
        banner.setUrl(uploadFileUrl);
        banner.setImage(uploadFileUrl);
        return toAjax(bannerService.updateById(banner));
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:banner:search')")
    @GetMapping(value = "/{bannerId}")
    public AjaxResult getInfo(@PathVariable Integer bannerId) {
        return success(bannerService.getById(bannerId));
    }
}
