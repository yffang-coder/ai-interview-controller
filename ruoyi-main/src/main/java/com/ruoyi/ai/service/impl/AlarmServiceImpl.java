package com.ruoyi.ai.service.impl;

import java.util.List;
import java.util.Date;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.AlarmMapper;
import com.ruoyi.ai.domain.Alarm;
import com.ruoyi.ai.service.IAlarmService;

/**
 * 告警Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-21
 */
@Service
public class AlarmServiceImpl implements IAlarmService
{
    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 查询告警
     *
     * @param alarmId 告警主键
     * @return 告警
     */
    @Override
    public Alarm selectAlarmByAlarmId(Long alarmId)
    {
        return alarmMapper.selectAlarmByAlarmId(alarmId);
    }

    /**
     * 查询告警列表
     *
     * @param alarm 告警
     * @return 告警
     */
    @Override
    public List<Alarm> selectAlarmList(Alarm alarm)
    {
        return alarmMapper.selectAlarmList(alarm);
    }

    /**
     * 新增告警
     *
     * @param alarm 告警
     * @return 结果
     */
    @Override
    public int insertAlarm(Alarm alarm)
    {
        alarm.setCreateTime(new Date());
        alarm.setTriggerTime(new Date());
        int result = alarmMapper.insertAlarm(alarm);

        // 如果告警创建成功，通过WebSocket推送给前端
        if (result > 0 && messagingTemplate != null) {
            try {
                // 只推送未处理的告警（status=0）
                if ("0".equals(alarm.getStatus())) {
                    messagingTemplate.convertAndSend("/topic/alarm", alarm);
                }
            } catch (Exception e) {
                // 记录日志但不影响主业务流程
                System.err.println("WebSocket推送告警失败: " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * 修改告警
     *
     * @param alarm 告警
     * @return 结果
     */
    @Override
    public int updateAlarm(Alarm alarm)
    {
        alarm.setUpdateTime(new Date());
        return alarmMapper.updateAlarm(alarm);
    }

    /**
     * 批量删除告警
     *
     * @param alarmIds 需要删除的告警主键集合
     * @return 结果
     */
    @Override
    public int deleteAlarmByAlarmIds(Long[] alarmIds)
    {
        return alarmMapper.deleteAlarmByAlarmIds(alarmIds);
    }

    /**
     * 删除告警信息
     *
     * @param alarmId 告警主键
     * @return 结果
     */
    @Override
    public int deleteAlarmByAlarmId(Long alarmId)
    {
        return alarmMapper.deleteAlarmByAlarmId(alarmId);
    }

    /**
     * 处理告警
     *
     * @param alarmId 告警ID
     * @param handler 处理人
     * @param handleResult 处理结果
     * @return 结果
     */
    @Override
    public int handleAlarm(Long alarmId, String handler, String handleResult)
    {
        Alarm alarm = new Alarm();
        alarm.setAlarmId(alarmId);
        alarm.setStatus("2"); // 已处理
        alarm.setHandler(handler);
        alarm.setHandleResult(handleResult);
        alarm.setHandleTime(new Date());
        alarm.setUpdateTime(new Date());
        return alarmMapper.updateAlarm(alarm);
    }
}