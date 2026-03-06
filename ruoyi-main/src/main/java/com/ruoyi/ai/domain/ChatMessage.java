package com.ruoyi.ai.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 聊天消息对象 sys_chat_message
 * 
 * @author ruoyi
 * @date 2025-01-28
 */
public class ChatMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    /** 发送者ID */
    @Excel(name = "发送者ID")
    private Long senderId;

    /** 接收者ID */
    @Excel(name = "接收者ID")
    private Long receiverId;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    /** 消息类型（0文本 1图片 2文件） */
    @Excel(name = "消息类型", readConverterExp = "0=文本,1=图片,2=文件")
    private String msgType;

    /** 是否已读（0未读 1已读） */
    @Excel(name = "是否已读", readConverterExp = "0=未读,1=已读")
    private String isRead;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }
    public void setSenderId(Long senderId) 
    {
        this.senderId = senderId;
    }

    public Long getSenderId() 
    {
        return senderId;
    }
    public void setReceiverId(Long receiverId) 
    {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() 
    {
        return receiverId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setMsgType(String msgType) 
    {
        this.msgType = msgType;
    }

    public String getMsgType() 
    {
        return msgType;
    }
    public void setIsRead(String isRead) 
    {
        this.isRead = isRead;
    }

    public String getIsRead() 
    {
        return isRead;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("messageId", getMessageId())
            .append("senderId", getSenderId())
            .append("receiverId", getReceiverId())
            .append("content", getContent())
            .append("msgType", getMsgType())
            .append("isRead", getIsRead())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
