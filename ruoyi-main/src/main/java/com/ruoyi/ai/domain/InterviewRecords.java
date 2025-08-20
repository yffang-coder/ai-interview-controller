package com.ruoyi.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
@Getter
@Setter
@TableName("ai_interview_interview_records")
public class InterviewRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String sessionId;

    private String role;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String openid;

    private String category;

    private String subject;

    private Integer delFlag;
}
