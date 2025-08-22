package com.ruoyi.framework.security.filter;

import com.ruoyi.ai.domain.WxLogin;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MPAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/mp") && !requestURI.contains("/mp/login")) {
            String authToken = request.getHeader("mp_token");
            WxLogin cacheObject = redisCache.getCacheObject(CacheConstants.WEXIN_LOGIN_KEY + authToken);
            if (cacheObject == null) {
                response.setContentType("application/json;charset=utf-8");
                logger.error("登录失败");
                response.getWriter().write("{\"code\":401,\"msg\":\"重新登录\"}");
            } else {
                //小程序端 做过期时间
//                long expire = redisCache.getExpire(CacheConstants.WEXIN_LOGIN_KEY + authToken);
//                if (expire < 20 * 60) {
//                    redisCache.setCacheObject(CacheConstants.WEXIN_LOGIN_KEY + authToken, cacheObject,
//                            60, TimeUnit.MINUTES);
//                }
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
