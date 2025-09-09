package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.SatisfactionDto;
import com.ruoyi.ai.domain.SatisfactionSurvey;
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
public interface ISatisfactionSurveyService extends IService<SatisfactionSurvey> {

    boolean submit(SatisfactionDto satisfactionDto, String openid);

    List<SatisfactionSurvey> getAllSatisfactionSurvey();
}
