package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.ai.domain.Banner;
import com.ruoyi.ai.domain.Category;
import com.ruoyi.ai.domain.WxLogin;
import com.ruoyi.ai.service.IBannerService;
import com.ruoyi.ai.service.ICategoryService;
import com.ruoyi.ai.service.IWxLoginService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mp")
public class MpController extends BaseController {

    @Autowired
    IWxLoginService wxLoginService;

    @Autowired
    ICategoryService iCategoryService;

    @Autowired
    IBannerService bannerService;

    @RequestMapping("/banners")
    public List<Banner> getAllBannerList() {
        List<Banner> allBannerList = bannerService.getAllBannerList();
        logger.info("allBannerList:{}", allBannerList);
        return allBannerList;
    }

    @RequestMapping("/categories")
    public List<Category> getAllCategoryList() {
        return iCategoryService.getAllCategoryList();
    }

    @RequestMapping("/login")
    public AjaxResult login(String code) {
        WxLogin wxLogin = wxLoginService.login(code);
        if (wxLogin.getOpenid() != null && !wxLogin.getOpenid().isEmpty()) {
            return success(wxLogin);
        }
        return AjaxResult.error(401, "登录失败");
    }
}
