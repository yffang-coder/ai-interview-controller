package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.ai.domain.*;
import com.ruoyi.ai.service.*;
import com.ruoyi.ai.service.impl.AnnouncementsServiceImpl;
import com.ruoyi.ai.service.impl.InterviewRecordsServiceImpl;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.LimitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    @Autowired
    IAiService aiService;

    @Autowired
    IModelsService iModelsService;

    @Autowired
    ICategoryItemService iCategoryItemService;

    @Autowired
    InterviewRecordsServiceImpl interviewRecordsService;

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


    /**
     * 和 ai 聊天
     *
     * @param mpRequest
     * @param openid
     * @return
     */
    @PostMapping("/chat")
    @RateLimiter(key = "mp-chat", time = 3, count = 1, limitType = LimitType.IDENTITY)
    public MpAnswer chat(@RequestBody MpRequest mpRequest, @RequestHeader("openid") String openid) {
        return aiService.chat(mpRequest, openid);
    }

    @GetMapping("/models")
    public List<Models> getAllModelList() {
        return iModelsService.getAllModelList();
    }

    @GetMapping("/categories/{category}")
    public List<CategoryItem> getCategoryItemList(@PathVariable String category) {
        return iCategoryItemService.getCategoryItemList(category);
    }

    @GetMapping("/records")
    public List<ChatRecords> getInterviewRecordsByPage(@RequestParam String subject,
                                                       @RequestParam String category,
                                                       @RequestHeader("openid") String openid,
                                                       @RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(name = "startTime", required = false) Long startTime,
                                                       @RequestParam(name = "endTime", required = false) Long endTime) {
        return interviewRecordsService.getInterviewRecordsByPage(subject, category, openid, pageNum, startTime, endTime);
    }

    @GetMapping("/records/menu")
    public List<MenuItem> getInterviewRecordsMenu() {
        return interviewRecordsService.getInterviewRecordsMenu();
    }

    @Autowired
    private AnnouncementsServiceImpl announcementsService;

    @GetMapping("/announcements")
    public List<Announcements> getAllAnnouncements(){
        return announcementsService.getAllAnnouncements();
    }

    @Autowired
    private ISatisfactionSurveyService satisfactionSurveyService;
    @PostMapping("/satisfaction")
    public AjaxResult submit(@RequestBody SatisfactionDto satisfactionDto, @RequestHeader("openid") String openid) {
        satisfactionSurveyService.submit(satisfactionDto, openid);
        return AjaxResult.success();
    }

    @GetMapping("/satisfaction")
    public List<SatisfactionSurvey> getAllSatisfactionSurvey() {
        return satisfactionSurveyService.getAllSatisfactionSurvey();
    }
}
