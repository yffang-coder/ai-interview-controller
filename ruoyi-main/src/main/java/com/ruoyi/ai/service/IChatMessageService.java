package com.ruoyi.ai.service;

import java.util.List;
import com.ruoyi.ai.domain.ChatMessage;

/**
 * 聊天消息Service接口
 * 
 * @author ruoyi
 * @date 2025-01-28
 */
public interface IChatMessageService 
{
    /**
     * 查询聊天消息
     * 
     * @param messageId 聊天消息主键
     * @return 聊天消息
     */
    public ChatMessage selectChatMessageByMessageId(Long messageId);

    /**
     * 查询聊天消息列表
     * 
     * @param chatMessage 聊天消息
     * @return 聊天消息集合
     */
    public List<ChatMessage> selectChatMessageList(ChatMessage chatMessage);

    /**
     * 查询两个用户之间的聊天记录
     *
     * @param userId1 用户1
     * @param userId2 用户2
     * @return 聊天消息集合
     */
    public List<ChatMessage> selectChatHistory(Long userId1, Long userId2);

    /**
     * 新增聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    public int insertChatMessage(ChatMessage chatMessage);

    /**
     * 修改聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    public int updateChatMessage(ChatMessage chatMessage);

    /**
     * 批量删除聊天消息
     * 
     * @param messageIds 需要删除的聊天消息主键集合
     * @return 结果
     */
    public int deleteChatMessageByMessageIds(Long[] messageIds);

    /**
     * 删除聊天消息信息
     * 
     * @param messageId 聊天消息主键
     * @return 结果
     */
    public int deleteChatMessageByMessageId(Long messageId);
}
