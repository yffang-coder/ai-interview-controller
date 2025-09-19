package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.ruoyi.ai.domain.Banner;
import com.ruoyi.ai.mapper.BannerMapper;
import com.ruoyi.ai.service.IBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {


    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> getAllBannerList() {
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Banner::getDelFlag, 1);
        queryWrapper.orderByAsc(Banner::getOrderNum);
        queryWrapper.select(Banner::getTitle, Banner::getUrl, Banner::getImage);
        return list(queryWrapper);
    }

    @Override
    public List<Banner> selectBannerList(Banner banner) {
        return bannerMapper.selectBannerList(banner);
    }

    @Override
    public boolean checkTitleExsit(Banner banner) {
        LambdaQueryWrapper<Banner> bannerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bannerLambdaQueryWrapper.eq(Banner::getTitle, banner.getTitle());
        bannerLambdaQueryWrapper.eq(Banner::getDelFlag, 1);
        if(banner.getId() != null){
            //更新操作 排除自身
            bannerLambdaQueryWrapper.ne(Banner::getId, banner.getId());
        }
        return exists(bannerLambdaQueryWrapper);
    }

    @Override
    public boolean deleteBanner(Long[] bannerids) {
        LambdaUpdateWrapper<Banner> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Banner::getId, bannerids);
        updateWrapper.set(Banner::getDelFlag, 0);
        return update(updateWrapper);
    }
}
