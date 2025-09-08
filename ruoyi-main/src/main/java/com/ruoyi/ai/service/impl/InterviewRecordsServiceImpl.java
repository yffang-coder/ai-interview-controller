package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.*;
import com.ruoyi.ai.mapper.InterviewRecordsMapper;
import com.ruoyi.ai.service.IInterviewRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.AiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@Service
public class InterviewRecordsServiceImpl extends ServiceImpl<InterviewRecordsMapper, InterviewRecords> implements IInterviewRecordsService {

    @Autowired
    InterviewRecordsMapper interviewRecordsMapper;

    @Autowired
    CategoryServiceImpl categoryService;


    @Override
    public List<AiChatMessage> loadChatMessageFromDB(String sessionId) {
        LambdaQueryWrapper<InterviewRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterviewRecords::getDelFlag, 1);
        queryWrapper.eq(InterviewRecords::getSessionId, sessionId);
        List<InterviewRecords> list = list(queryWrapper);
        return list.stream().map(item -> {
            AiChatMessage aiChatMessage = new AiChatMessage();
            aiChatMessage.setRole(item.getRole());
            aiChatMessage.setContent(item.getContent());
            return aiChatMessage;
        }).toList();
    }

    @Override
    public void saveMessage(MpRequest mpRequest, List<AiChatMessage> aiChatMessages, String openid) {
        List<InterviewRecords> records = aiChatMessages.stream().map(m -> {
            InterviewRecords ir = new InterviewRecords();
            //完整对话内容 提示词处理过的
            ir.setContent(m.getContent());
            ir.setSessionId(mpRequest.getSessionId());
            ir.setRole(m.getRole());
            ir.setOpenid(openid);
            //用户给的提示词 小程序发来的关键字
            ir.setCategory(mpRequest.getContent());
            ir.setSubject(mpRequest.getSubject());
            return ir;
        }).collect(Collectors.toList());
        saveBatch(records);
    }

    public List<ChatRecords> getInterviewRecordsByPage(String subject, String category, String openid,
                                                       int pageNum, Long startTime, Long endTime) {
        //分页查询注意：
        //前端 需要的10条数据 是代表10个不同的sessionId 即 10道不同的题目
        List<InterviewRecords> interviewRecords = interviewRecordsMapper
                .getInterviewRecordsByPage(subject, openid, category, startTime, endTime,
                        (pageNum - 1) * AiConstants.PAGE_SIZE, AiConstants.PAGE_SIZE);

        List<ChatRecords> chatRecords = new ArrayList<>();
        String lastSessionId = null;
        ChatRecords cr = null;
        for (InterviewRecords interviewRecords1 : interviewRecords) {
            if (interviewRecords1.getSessionId().equals(lastSessionId)) {
                cr.setAnswer(interviewRecords1);
            } else {
                cr = new ChatRecords();
                cr.setQuestion(interviewRecords1);
                chatRecords.add(cr);
            }
            lastSessionId = interviewRecords1.getSessionId();
        }
        return chatRecords;
    }

    @Override
    public List<MenuItem> getInterviewRecordsMenu() {
        return categoryService.getMenus();
    }
}
