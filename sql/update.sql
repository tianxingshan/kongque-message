CREATE TABLE `t_message` (
  `c_id` varchar(40) CHARACTER SET utf8 NOT NULL,
  `c_content` text CHARACTER SET utf8 COMMENT '内容',
  `c_theme` varchar(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT '消息标题',
  `c_create_time` datetime DEFAULT NULL COMMENT '后台下发时间',
  `c_user_id` text CHARACTER SET utf8 COMMENT '指定推送的用户id',
  `c_flag` varchar(4) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否完成推送 0-未推送 1-已推送',
  `c_push_time` datetime DEFAULT NULL COMMENT '推送时间',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

