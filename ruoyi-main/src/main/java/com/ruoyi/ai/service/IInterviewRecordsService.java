package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.AiChatMessage;
import com.ruoyi.ai.domain.InterviewRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.ai.domain.MpRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
public interface IInterviewRecordsService extends IService<InterviewRecords> {

    List<AiChatMessage> loadChatMessageFromDB(String sessionId);

    void saveMessage(MpRequest mpRequest, List<AiChatMessage> aiChatMessages, String openid);
}
