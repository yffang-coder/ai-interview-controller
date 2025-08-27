package com.ruoyi.framework.web.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.Models;
import com.ruoyi.ai.service.IModelsService;
import com.ruoyi.common.constant.AiConstants;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheWarmUpService implements ApplicationRunner {
    @Autowired
    RedisCache redisCache;

    @Autowired
    IModelsService iModelsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LambdaQueryWrapper<Models> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Models::getDelFlag, 1);
        List<Models> list = iModelsService.list(queryWrapper);
        for (Models model : list){
            redisCache.setCacheMapValue(AiConstants.AI_MODEL_NAME_KEY, model.getName(),model);
        }
    }
}
