package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mp")
public class MpController {
    @RequestMapping("/login")
    public AjaxResult login(){
        return AjaxResult.success();
    }
}
