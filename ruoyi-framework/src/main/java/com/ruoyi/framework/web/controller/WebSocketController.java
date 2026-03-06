package com.ruoyi.framework.web.controller;

import com.ruoyi.ai.domain.Alarm;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * WebSocket控制器
 * 处理客户端发送的消息路由
 *
 * @author ruoyi
 */
@Controller
public class WebSocketController {

    /**
     * 处理客户端发送的告警消息（可选，用于双向通信）
     */
    @MessageMapping("/alarm")
    @SendTo("/topic/alarm")
    public Alarm handleAlarmMessage(Alarm alarm) {
        return alarm;
    }
}