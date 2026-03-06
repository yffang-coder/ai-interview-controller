package com.ruoyi.web.controller.ai_interview;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.ai.domain.Alarm;
import com.ruoyi.ai.service.IAlarmService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警Controller
 *
 * @author ruoyi
 * @date 2025-01-21
 */
@RestController
@RequestMapping("/alarm")
public class AlarmController extends BaseController
{
    @Autowired
    private IAlarmService alarmService;

    /**
     * 查询告警列表
     */
    @PreAuthorize("@ss.hasPermi('ai:alarm:list')")
    @GetMapping("/list")
    public TableDataInfo list(Alarm alarm)
    {
        startPage();
        List<Alarm> list = alarmService.selectAlarmList(alarm);
        return getDataTable(list);
    }

    /**
     * 导出告警列表
     */
    @PreAuthorize("@ss.hasPermi('ai:alarm:export')")
    @Log(title = "告警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Alarm alarm)
    {
        List<Alarm> list = alarmService.selectAlarmList(alarm);
        // ExcelUtil<Alarm> util = new ExcelUtil<Alarm>(Alarm.class);
        // util.exportExcel(response, list, "告警数据");
    }

    /**
     * 获取告警详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:alarm:query')")
    @GetMapping(value = "/{alarmId}")
    public AjaxResult getInfo(@PathVariable("alarmId") Long alarmId)
    {
        return success(alarmService.selectAlarmByAlarmId(alarmId));
    }

    /**
     * 新增告警
     */
    @PreAuthorize("@ss.hasPermi('ai:alarm:add')")
    @Log(title = "告警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Alarm alarm)
    {
        return toAjax(alarmService.insertAlarm(alarm));
    }

    /**
     * 修改告警
     */
    @PreAuthorize("@ss.hasPermi('ai:alarm:edit')")
    @Log(title = "告警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Alarm alarm)
    {
        return toAjax(alarmService.updateAlarm(alarm));
    }

    /**
     * 删除告警
     */
    @PreAuthorize("@ss.hasPermi('ai:alarm:remove')")
    @Log(title = "告警", businessType = BusinessType.DELETE)
    @DeleteMapping("/{alarmIds}")
    public AjaxResult remove(@PathVariable Long[] alarmIds)
    {
        return toAjax(alarmService.deleteAlarmByAlarmIds(alarmIds));
    }

    /**
     * 处理告警
     */
    @PreAuthorize("@ss.hasPermi('ai:alarm:handle')")
    @Log(title = "告警", businessType = BusinessType.UPDATE)
    @PutMapping("/handle/{alarmId}")
    public AjaxResult handle(@PathVariable Long alarmId, @RequestBody Alarm alarm)
    {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        return toAjax(alarmService.handleAlarm(alarmId, currentUser.getNickName(), alarm.getHandleResult()));
    }
}