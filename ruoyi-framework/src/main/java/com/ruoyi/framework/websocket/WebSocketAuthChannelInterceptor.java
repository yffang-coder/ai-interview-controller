package com.ruoyi.framework.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {
    
    private static final Logger log = LoggerFactory.getLogger(WebSocketAuthChannelInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> headers = accessor.getNativeHeader("Authorization");
            String auth = (headers != null && !headers.isEmpty()) ? headers.get(0) : null;
            if (StringUtils.isNotEmpty(auth)) {
                String token = auth.startsWith("Bearer ") ? auth.substring(7) : auth;
                try {
                    String username = tokenService.getUsernameFromToken(token);
                    if (StringUtils.isNotEmpty(username)) {
                        // Avoid using UserDetailsService.loadUserByUsername as it triggers password validation
                        // which fails in WebSocket context (AuthenticationContextHolder is empty)
                        SysUser user = userService.selectUserByUserName(username);
                        
                        if (user != null) {
                            if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
                                log.warn("User {} is deleted, rejecting WebSocket connection", username);
                            } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
                                log.warn("User {} is disabled, rejecting WebSocket connection", username);
                            } else {
                                // Create LoginUser manually without password validation
                                UserDetails userDetails = new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                accessor.setUser(authentication);
                                log.debug("WebSocket authentication successful for user: {}", username);
                            }
                        } else {
                            log.warn("SysUser not found for username: {}", username);
                        }
                    }
                } catch (Exception e) {
                    log.error("WebSocket authentication failed: {}", e.getMessage());
                }
            }
        }
        return message;
    }
}
