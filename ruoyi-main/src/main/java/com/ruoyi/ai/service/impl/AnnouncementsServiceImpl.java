package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.Announcements;
import com.ruoyi.ai.domain.Models;
import com.ruoyi.ai.mapper.AnnouncementsMapper;
import com.ruoyi.ai.service.IAnnouncementsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-09-09
 */
@Service
public class AnnouncementsServiceImpl extends ServiceImpl<AnnouncementsMapper, Announcements> implements IAnnouncementsService {


    @Override
    public List<Announcements> getAllAnnouncements() {
        LambdaQueryWrapper<Announcements> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Announcements::getDelFlag, 1);
        queryWrapper.select(Announcements::getTitle,Announcements::getContent,Announcements::getCreateTime);
        return list(queryWrapper);
    }
}
