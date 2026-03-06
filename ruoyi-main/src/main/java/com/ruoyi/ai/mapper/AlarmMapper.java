package com.ruoyi.ai.mapper;

import java.util.List;
import com.ruoyi.ai.domain.Alarm;

/**
 * 告警Mapper接口
 *
 * @author ruoyi
 * @date 2025-01-21
 */
public interface AlarmMapper
{
    /**
     * 查询告警
     *
     * @param alarmId 告警主键
     * @return 告警
     */
    public Alarm selectAlarmByAlarmId(Long alarmId);

    /**
     * 查询告警列表
     *
     * @param alarm 告警
     * @return 告警集合
     */
    public List<Alarm> selectAlarmList(Alarm alarm);

    /**
     * 新增告警
     *
     * @param alarm 告警
     * @return 结果
     */
    public int insertAlarm(Alarm alarm);

    /**
     * 修改告警
     *
     * @param alarm 告警
     * @return 结果
     */
    public int updateAlarm(Alarm alarm);

    /**
     * 删除告警
     *
     * @param alarmId 告警主键
     * @return 结果
     */
    public int deleteAlarmByAlarmId(Long alarmId);

    /**
     * 批量删除告警
     *
     * @param alarmIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAlarmByAlarmIds(Long[] alarmIds);
}