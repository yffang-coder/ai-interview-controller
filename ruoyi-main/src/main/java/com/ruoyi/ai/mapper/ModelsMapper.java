package com.ruoyi.ai.mapper;

import com.ruoyi.ai.domain.Models;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
public interface ModelsMapper extends BaseMapper<Models> {

    List<Models> selectModelsList(Models models);
}
