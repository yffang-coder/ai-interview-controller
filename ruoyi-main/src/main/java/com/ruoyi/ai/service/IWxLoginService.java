package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.WxLogin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
public interface IWxLoginService extends IService<WxLogin> {

    WxLogin login(String code);
}
