package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.Banner;
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
public interface IBannerService extends IService<Banner> {

    List<Banner> getAllBannerList();

    List<Banner> selectBannerList(Banner banner);

    boolean checkTitleExsit(Banner banner);

    boolean deleteBanner(Long[] bannerids);
}
