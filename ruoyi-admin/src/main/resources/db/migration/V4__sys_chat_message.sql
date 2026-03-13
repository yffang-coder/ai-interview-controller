-- ----------------------------
-- 聊天消息表
-- 来源：ruoyi-main/src/main/resources/mapper/ai/chat_init.sql
-- ----------------------------
drop table if exists sys_chat_message;
create table sys_chat_message (
  message_id           bigint(20)      not null auto_increment    comment '消息ID',
  sender_id            bigint(20)      default null               comment '发送者ID',
  receiver_id          bigint(20)      default null               comment '接收者ID',
  content              varchar(2000)   default null               comment '消息内容',
  msg_type             char(1)         default '0'                comment '消息类型（0文本 1图片 2文件）',
  is_read              char(1)         default '0'                comment '是否已读（0未读 1已读）',
  del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by            varchar(64)     default ''                 comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default null               comment '备注',
  primary key (message_id)
) engine=innodb auto_increment=1 comment = '聊天消息表';

