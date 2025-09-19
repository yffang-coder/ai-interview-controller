package com.ruoyi.ai.mapper;

import com.ruoyi.ai.domain.Banner;
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
public interface BannerMapper extends BaseMapper<Banner> {

    List<Banner> selectBannerList(Banner banner);
}
