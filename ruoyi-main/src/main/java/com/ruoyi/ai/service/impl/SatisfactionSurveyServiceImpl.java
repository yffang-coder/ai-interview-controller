package com.ruoyi.ai.service.impl;

import com.ruoyi.ai.domain.SatisfactionDto;
import com.ruoyi.ai.domain.SatisfactionSurvey;
import com.ruoyi.ai.mapper.SatisfactionSurveyMapper;
import com.ruoyi.ai.service.ISatisfactionSurveyService;
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
public class SatisfactionSurveyServiceImpl extends ServiceImpl<SatisfactionSurveyMapper, SatisfactionSurvey> implements ISatisfactionSurveyService {

    @Override
    public boolean submit(SatisfactionDto satisfactionDto, String openid) {
        SatisfactionSurvey satisfactionSurvey = new SatisfactionSurvey();
        satisfactionSurvey.setOpenid(openid);
        satisfactionSurvey.setRating(satisfactionDto.getRating());
        satisfactionSurvey.setFeedback(satisfactionDto.getFeedback());
        return save(satisfactionSurvey);
    }

    @Override
    public List<SatisfactionSurvey> getAllSatisfactionSurvey() {
        return list();
    }
}
