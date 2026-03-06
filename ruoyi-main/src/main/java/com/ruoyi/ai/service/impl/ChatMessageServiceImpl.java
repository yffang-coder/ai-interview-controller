package com.ruoyi.ai.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.ChatMessageMapper;
import com.ruoyi.ai.domain.ChatMessage;
import com.ruoyi.ai.service.IChatMessageService;

/**
 * 聊天消息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-28
 */
@Service
public class ChatMessageServiceImpl implements IChatMessageService 
{
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    /**
     * 查询聊天消息
     * 
     * @param messageId 聊天消息主键
     * @return 聊天消息
     */
    @Override
    public ChatMessage selectChatMessageByMessageId(Long messageId)
    {
        return chatMessageMapper.selectChatMessageByMessageId(messageId);
    }

    /**
     * 查询聊天消息列表
     * 
     * @param chatMessage 聊天消息
     * @return 聊天消息
     */
    @Override
    public List<ChatMessage> selectChatMessageList(ChatMessage chatMessage)
    {
        return chatMessageMapper.selectChatMessageList(chatMessage);
    }

    /**
     * 查询两个用户之间的聊天记录
     */
    @Override
    public List<ChatMessage> selectChatHistory(Long userId1, Long userId2)
    {
        return chatMessageMapper.selectChatHistory(userId1, userId2);
    }

    /**
     * 新增聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    @Override
    public int insertChatMessage(ChatMessage chatMessage)
    {
        chatMessage.setCreateTime(DateUtils.getNowDate());
        return chatMessageMapper.insertChatMessage(chatMessage);
    }

    /**
     * 修改聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    @Override
    public int updateChatMessage(ChatMessage chatMessage)
    {
        chatMessage.setUpdateTime(DateUtils.getNowDate());
        return chatMessageMapper.updateChatMessage(chatMessage);
    }

    /**
     * 批量删除聊天消息
     * 
     * @param messageIds 需要删除的聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageByMessageIds(Long[] messageIds)
    {
        return chatMessageMapper.deleteChatMessageByMessageIds(messageIds);
    }

    /**
     * 删除聊天消息信息
     * 
     * @param messageId 聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageByMessageId(Long messageId)
    {
        return chatMessageMapper.deleteChatMessageByMessageId(messageId);
    }
}
