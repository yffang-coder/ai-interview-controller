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
@TableName("ai_interview_models")
public class Models implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String url;

    private String questionPrompt;

    private String answerPrompt;

    private String role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer delFlag;

    /**
     * 免费 1 收费
     */
    private Integer charge;

    /**
     * 单选对话 1 多轮对话
     */
    private Integer multiple;

    private Integer orderNum;
}
