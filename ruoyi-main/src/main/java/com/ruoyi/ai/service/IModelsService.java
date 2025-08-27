package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.Models;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
public interface IModelsService extends IService<Models> {

    List<Models> getAllModelList();
}
