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
@TableName("wx_login")
public class WxLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String openid;

    private String sessionKey;

    private String unionid;

    private String errcode;

    private String errmsg;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
