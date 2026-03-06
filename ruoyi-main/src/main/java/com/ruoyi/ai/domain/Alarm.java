package com.ruoyi.ai.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 告警对象 alarm
 *
 * @author ruoyi
 * @date 2025-01-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Alarm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 告警ID */
    private Long alarmId;

    /** 告警标题 */
    @Excel(name = "告警标题")
    private String alarmTitle;

    /** 告警级别 (0=低级 1=中级 2=高级 3=紧急) */
    @Excel(name = "告警级别", readConverterExp = "0=低级,1=中级,2=高级,3=紧急")
    private String alarmLevel;

    /** 告警类型 */
    @Excel(name = "告警类型")
    private String alarmType;

    /** 告警内容 */
    @Excel(name = "告警内容")
    private String alarmContent;

    /** 告警状态 (0=未处理 1=处理中 2=已处理 3=已忽略) */
    @Excel(name = "告警状态", readConverterExp = "0=未处理,1=处理中,2=已处理,3=已忽略")
    private String status;

    /** 触发时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "触发时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date triggerTime;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handler;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;

    /** 告警来源 */
    @Excel(name = "告警来源")
    private String alarmSource;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}