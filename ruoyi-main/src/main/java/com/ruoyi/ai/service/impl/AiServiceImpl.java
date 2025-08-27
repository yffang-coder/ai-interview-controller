package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.ai.domain.*;
import com.ruoyi.ai.service.IAiService;
import com.ruoyi.ai.service.IInterviewRecordsService;
import com.ruoyi.ai.service.IModelsService;
import com.ruoyi.common.config.AiProperties;
import com.ruoyi.common.config.MpProperties;
import com.ruoyi.common.constant.AiConstants;
import com.ruoyi.common.core.redis.RedisCache;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableConfigurationProperties(AiProperties.class)
public class AiServiceImpl implements IAiService {
    @Autowired
    RedisCache redisCache;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IModelsService iModelsService;

    @Autowired
    IInterviewRecordsService iInterviewRecordsService;

    @Autowired
    AiProperties aiProperties;

    @Override
    public MpAnswer chat(MpRequest mpRequest, String openid) {
        Models models = getModels(mpRequest.getModelName());
        boolean isNew = false;
        //读取当前回话的历史聊天数据
        List<AiChatMessage> aiChatMessageList = loadChatMessage(mpRequest.getSessionId());
        if (aiChatMessageList.isEmpty()) {
            //第一次回话创建一个角色交代
            isNew = true;
            aiChatMessageList.add(new AiChatMessage("system", models.getRole()));
        }
        AiChatMessage aiChatMessage = new AiChatMessage();
        aiChatMessage.setRole(AiConstants.MESSAGE_ROLE_USER);
        if (AiConstants.MP_REQUEST_TYPE_Q.equals(mpRequest.getType())) {
            aiChatMessage.setContent(String.format(models.getQuestionPrompt(), mpRequest.getContent()));
        } else if (AiConstants.MP_REQUEST_TYPE_A.equals(mpRequest.getType())) {
            aiChatMessage.setContent(String.format(models.getAnswerPrompt(), mpRequest.getContent()));
        } else {
            throw new RuntimeException("请求类型错误");
        }
        aiChatMessageList.add(aiChatMessage);
        AiRequestBody requestBody = new AiRequestBody();
        //models.getRole() 无法指定角色
        requestBody.setMessages(aiChatMessageList);
        requestBody.setUser(openid);
        requestBody.setModel(models.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + aiProperties.getToken());

        HttpEntity<AiRequestBody> request = new HttpEntity<>(requestBody, headers);

        //发送请求
        AiResponseBody aiResponseBody = restTemplate.postForObject(aiProperties.getToken_url(),
                request, AiResponseBody.class);

        AiChatMessage chatMessage = new AiChatMessage();
        chatMessage.setRole(AiConstants.MESSAGE_ROLE_ASSISTANT);
        assert aiResponseBody != null;
        chatMessage.setContent(aiResponseBody.getChoices().get(0).getMessage().getContent());
        aiChatMessageList.add(chatMessage);
        cacheMessage(aiChatMessageList, mpRequest.getSessionId());
        if (isNew) {
            //加上角色内容
            iInterviewRecordsService.saveMessage(mpRequest, aiChatMessageList.subList(aiChatMessageList.size() - 3,
                    aiChatMessageList.size()), openid);
        } else {
            iInterviewRecordsService.saveMessage(mpRequest, aiChatMessageList.subList(aiChatMessageList.size() - 2,
                    aiChatMessageList.size()), openid);
        }
        MpAnswer mpAnswer = new MpAnswer();
        mpAnswer.setContent(chatMessage.getContent());
        mpAnswer.setType(AiConstants.MP_QUESTION_TYPE_CHOICE);
        return mpAnswer;
    }

    private void cacheMessage(List<AiChatMessage> aiChatMessageList, String sessionId) {
        redisCache.setCacheList(AiConstants.AI_SESSION_PREFIX + sessionId, aiChatMessageList);

    }

    private List<AiChatMessage> loadChatMessage(String sessionId) {
        List<AiChatMessage> aiChatMessageList = redisCache.getCacheList(
                AiConstants.AI_SESSION_PREFIX + sessionId);
        if (aiChatMessageList == null || aiChatMessageList.isEmpty()) {
            aiChatMessageList = loadChatMessageFromDB(sessionId);
            if (aiChatMessageList == null || aiChatMessageList.isEmpty()) {
                aiChatMessageList = new ArrayList<>();
            }
        }
        return aiChatMessageList;
    }

    private List<AiChatMessage> loadChatMessageFromDB(String sessionId) {
        List<AiChatMessage> aiChatMessageList = iInterviewRecordsService.loadChatMessageFromDB(sessionId);
        return aiChatMessageList;
    }

    private Models getModels(String modelName) {
        if (Strings.isBlank(modelName)) {
            modelName = AiConstants.AI_MODEL_NAME_DEFAULT;
        }
        Models cacheMapValue = redisCache.getCacheMapValue(AiConstants.AI_MODEL_NAME_KEY, modelName);
        if (cacheMapValue == null) {
            LambdaQueryWrapper<Models> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Models::getName, modelName);
            queryWrapper.eq(Models::getDelFlag, 1);
            cacheMapValue = iModelsService.getOne(queryWrapper);
            if (cacheMapValue == null) {
                throw new RuntimeException("模型不存在");
            }
            redisCache.setCacheMapValue(AiConstants.AI_MODEL_NAME_KEY, modelName, cacheMapValue);
        }
        return cacheMapValue;
    }
}
