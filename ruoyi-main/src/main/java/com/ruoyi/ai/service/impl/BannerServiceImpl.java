package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.Banner;
import com.ruoyi.ai.mapper.BannerMapper;
import com.ruoyi.ai.service.IBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Override
    public List<Banner> getAllBannerList() {
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Banner::getDelFlag, 1);
        queryWrapper.orderByAsc(Banner::getOrderNum);
        queryWrapper.select(Banner::getTitle, Banner::getUrl, Banner::getImage);
        return list(queryWrapper);
    }
}
