package com.ruoyi.web.controller.ai_interview;

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.ai.domain.ChatMessage;
import com.ruoyi.ai.service.IChatMessageService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 聊天消息Controller
 * 
 * @author ruoyi
 * @date 2025-01-28
 */
@RestController
@RequestMapping("/ai/chat")
public class ChatController extends BaseController
{
    @Autowired
    private IChatMessageService chatMessageService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 查询聊天消息列表
     */
    @PreAuthorize("@ss.hasPermi('ai:chat:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChatMessage chatMessage)
    {
        startPage();
        List<ChatMessage> list = chatMessageService.selectChatMessageList(chatMessage);
        return getDataTable(list);
    }

    /**
     * 获取历史消息
     */
    @GetMapping("/history/{userId}")
    public AjaxResult history(@PathVariable("userId") Long targetUserId)
    {
        Long currentUserId = SecurityUtils.getUserId();
        List<ChatMessage> list = chatMessageService.selectChatHistory(currentUserId, targetUserId);
        return AjaxResult.success(list);
    }

    /**
     * 获取所有用户列表（用于聊天选择）
     */
    @GetMapping("/users")
    public AjaxResult getUsers()
    {
        List<SysUser> list = userService.selectAllUsers();
        Long currentUserId = SecurityUtils.getUserId();
        List<SysUser> filtered = list.stream()
                .filter(u -> u.getUserId() != null && !u.getUserId().equals(currentUserId))
                .collect(Collectors.toList());
        return AjaxResult.success(filtered);
    }

    /**
     * 获取除当前用户外的所有用户列表
     */
    @GetMapping("/users/excludeSelf")
    public AjaxResult getUsersExcludeSelf()
    {
        List<SysUser> list = userService.selectAllUsers();
        Long currentUserId = SecurityUtils.getUserId();
        List<SysUser> filtered = list.stream()
                .filter(u -> u.getUserId() != null && !u.getUserId().equals(currentUserId))
                .collect(Collectors.toList());
        return AjaxResult.success(filtered);
    }

    /**
     * 发送消息 (HTTP方式，也可以走WebSocket)
     */
    @Log(title = "聊天消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChatMessage chatMessage)
    {
        chatMessage.setSenderId(SecurityUtils.getUserId());
        chatMessage.setCreateBy(SecurityUtils.getUsername());
        chatMessage.setCreateTime(new Date());
        
        // 保存到数据库
        chatMessageService.insertChatMessage(chatMessage);

        // WebSocket 推送
        SysUser receiver = userService.selectUserById(chatMessage.getReceiverId());
        if (receiver != null && receiver.getUserName() != null) {
            messagingTemplate.convertAndSendToUser(receiver.getUserName(), "/queue/chat", chatMessage);
        }
        String senderUsername = SecurityUtils.getUsername();
        if (senderUsername != null) {
            messagingTemplate.convertAndSendToUser(senderUsername, "/queue/chat", chatMessage);
        }
        
        return AjaxResult.success(chatMessage);
    }
}
