-- ----------------------------
-- 告警表与初始化数据
-- 来源：sql/alarm.sql
-- ----------------------------
drop table if exists ai_alarm;
create table ai_alarm (
  alarm_id           bigint(20)      not null auto_increment    comment '告警ID',
  alarm_title        varchar(255)    default ''                 comment '告警标题',
  alarm_level        varchar(10)     default '0'                comment '告警级别 (0=低级 1=中级 2=高级 3=紧急)',
  alarm_type         varchar(100)    default ''                 comment '告警类型',
  alarm_content      text                                       comment '告警内容',
  status             varchar(10)     default '0'                comment '告警状态 (0=未处理 1=处理中 2=已处理 3=已忽略)',
  trigger_time       datetime                                   comment '触发时间',
  handle_time        datetime                                   comment '处理时间',
  handler            varchar(100)    default ''                 comment '处理人',
  handle_result      text                                       comment '处理结果',
  alarm_source       varchar(255)    default ''                 comment '告警来源',
  del_flag           char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (alarm_id)
) engine=innodb auto_increment=100 comment = '告警表';

insert into ai_alarm values(1, '系统CPU使用率过高', '2', '系统监控', '服务器CPU使用率达到95%，可能影响系统性能', '0', sysdate(), null, '', '', '系统监控', '0', 'admin', sysdate(), '', null, '需要立即处理');
insert into ai_alarm values(2, '数据库连接池满载', '1', '数据库监控', '数据库连接池使用率达到90%', '1', date_sub(sysdate(), interval 1 hour), null, 'admin', '已调整连接池配置', '数据库监控', '0', 'admin', date_sub(sysdate(), interval 1 hour), 'admin', sysdate(), '处理完成');
insert into ai_alarm values(3, '磁盘空间不足', '3', '存储监控', '系统磁盘剩余空间不足10GB', '0', date_sub(sysdate(), interval 30 minute), null, '', '', '存储监控', '0', 'admin', date_sub(sysdate(), interval 30 minute), '', null, '紧急清理磁盘空间');

