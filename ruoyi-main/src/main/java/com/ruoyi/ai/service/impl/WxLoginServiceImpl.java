package com.ruoyi.ai.service.impl;

import com.ruoyi.ai.domain.WxLogin;
import com.ruoyi.ai.mapper.WxLoginMapper;
import com.ruoyi.ai.service.IWxLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.config.MpProperties;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@Service
@EnableConfigurationProperties(MpProperties.class)
public class WxLoginServiceImpl extends ServiceImpl<WxLoginMapper, WxLogin> implements IWxLoginService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MpProperties mpProperties;

    @Autowired
    RedisCache redisCache;

    private static final Logger logger = LoggerFactory.
            getLogger(WxLoginServiceImpl.class);

    @Override
    public WxLogin login(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("js_code", code);
        params.put("appid", mpProperties.getAppid());
        params.put("secret", mpProperties.getSecret());
        WxLogin wxLogin
                = restTemplate.getForObject(mpProperties.getTokenUrl(), WxLogin.class, params);
        logger.info("微信登录返回结果：{}", wxLogin.toString());
        // 会话密钥，用于后续解密用户数据
        //认证 用uuid，唯一标识用 openid
        wxLogin.setSessionKey(UUID.randomUUID().toString());
        if (wxLogin.getErrmsg() == null || (wxLogin.getOpenid() != null && !wxLogin.getOpenid().isEmpty())) {
            //// 用户在当前小程序的唯一标识
            //保存唯一标识到数据库
            save(wxLogin);
            save2Redis(wxLogin);
        }
        return wxLogin;
    }

    private void save2Redis(WxLogin wxLogin) {
        redisCache.setCacheObject(CacheConstants.WEXIN_LOGIN_KEY + wxLogin.getSessionKey(),
                wxLogin, 60, TimeUnit.MINUTES);
    }
}
