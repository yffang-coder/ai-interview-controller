package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.ai.domain.Models;
import com.ruoyi.ai.mapper.ModelsMapper;
import com.ruoyi.ai.service.IModelsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.AiModelsEnums;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ModelsMapper modelsMapper;

    @Override
    public List<Models> getAllModelList() {
        LambdaQueryWrapper<Models> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Models::getDelFlag, 1);
        queryWrapper.eq(Models::getCharge, 0);
        queryWrapper.select(Models::getName,Models::getCharge,Models::getMultiple);
        queryWrapper.orderByAsc(Models::getOrderNum);
        List<Models>  list = list(queryWrapper);
        return list.stream().peek(item -> {
            AiModelsEnums byModelName = AiModelsEnums.getByModelName(item.getName());
            if (byModelName != null) {
                item.setName(byModelName.getUeName());
            }
        }).toList();
    }

    @Override
    public List<Models> selectModelsList(Models models) {
        return modelsMapper.selectModelsList(models);
    }

    @Override
    public boolean checkNameExsit(Models models) {

        LambdaQueryWrapper<Models> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Models::getDelFlag,1);
        queryWrapper.eq(Models::getName,models.getName());
        if (models.getId() != null){
            queryWrapper.ne(Models::getId,models.getId());
        }
        return exists(queryWrapper);
    }

    @Override
    public boolean deleteModels(Long[] modelsIds) {
        LambdaUpdateWrapper<Models> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Models::getId,modelsIds);
        updateWrapper.set(Models::getDelFlag,0);
        return update(updateWrapper);
    }
}
