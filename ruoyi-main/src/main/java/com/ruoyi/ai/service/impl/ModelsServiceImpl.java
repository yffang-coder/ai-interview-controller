package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.Models;
import com.ruoyi.ai.mapper.ModelsMapper;
import com.ruoyi.ai.service.IModelsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.AiModelsEnums;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@Service
public class ModelsServiceImpl extends ServiceImpl<ModelsMapper, Models> implements IModelsService {

    private List<Models> list;

    @Override
    public List<Models> getAllModelList() {
        LambdaQueryWrapper<Models> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Models::getDelFlag, 1);
        queryWrapper.eq(Models::getCharge, 0);
        queryWrapper.select(Models::getName,Models::getCharge,Models::getMultiple);
        queryWrapper.orderByAsc(Models::getOrderNum);
        list = list(queryWrapper);
        return list.stream().peek(item -> {
            AiModelsEnums byModelName = AiModelsEnums.getByModelName(item.getName());
            if (byModelName != null) {
                item.setName(byModelName.getUeName());
            }
        }).toList();
    }
}
