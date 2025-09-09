package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.Announcements;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ruoyi
 * @since 2025-09-09
 */
public interface IAnnouncementsService extends IService<Announcements> {

    List<Announcements> getAllAnnouncements();
}
