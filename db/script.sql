CREATE TABLE `category` (
  `id` varchar(50) NOT NULL,
  `category_name` varchar(200) NOT NULL,
  `category_type` varchar(100) NOT NULL,
  `parent` varchar(100) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `edge` (
  `flow_id` varchar(50) NOT NULL,
  `edge_id` varchar(50) NOT NULL,
  `from_node_id` varchar(50) NOT NULL,
  `to_node_id` varchar(50) NOT NULL,
  `edge_condition` varchar(1000) DEFAULT NULL,
  `edge_exception_return_type` int(11) NOT NULL,
  `skip_flag` int(11) NOT NULL,
  `skip_value` varchar(100) DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`edge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `flow` (
  `flow_id` varchar(50) NOT NULL,
  `category_id` varchar(50) NOT NULL,
  `flow_name` varchar(100) NOT NULL,
  `flow_desc` varchar(100) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_roles` (
  `roles_id` varchar(50) NOT NULL,
  `roles_name` varchar(100) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `node` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `node_name` varchar(100) NOT NULL,
  `ref_name` varchar(100) NOT NULL,
  `node_type` int(11) NOT NULL,
  `start_type` int(11) NOT NULL,
  `execute_type` int(11) NOT NULL,
  `start_cron` varchar(100) DEFAULT NULL,
  `sub_flow_id` varchar(50) DEFAULT NULL,
  `sub_node_id` varchar(50) DEFAULT NULL,
  `layout_x` varchar(100) DEFAULT NULL,
  `layout_y` varchar(100) DEFAULT NULL,
  `skip_flag` int(11) NOT NULL,
  `skip_value` varchar(100) DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `node_http` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `timeout` varchar(100) DEFAULT NULL,
  `timeout_type` int(11) NOT NULL,
  `success_code` varchar(100) NOT NULL,
  `error_type` int(11) NOT NULL,
  `sync_flag` int(11) NOT NULL,
  `http_url` varchar(1000) DEFAULT NULL,
  `http_method` varchar(100) DEFAULT NULL,
  `http_header` varchar(1000) DEFAULT NULL,
  `http_body` varchar(1000) DEFAULT NULL,
  `http_content_type` varchar(100) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `node_roles` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `roles_id` varchar(50) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`,`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `node_shell` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `timeout` varchar(100) DEFAULT NULL,
  `timeout_type` int(11) NOT NULL,
  `success_code` varchar(100) NOT NULL,
  `error_type` int(11) NOT NULL,
  `sync_flag` int(11) NOT NULL,
  `shell_location` varchar(1000) DEFAULT NULL,
  `shell_param` varchar(1000) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `publish_edge` (
  `flow_id` varchar(50) NOT NULL,
  `edge_id` varchar(50) NOT NULL,
  `from_node_id` varchar(50) NOT NULL,
  `to_node_id` varchar(50) NOT NULL,
  `edge_condition` varchar(1000) DEFAULT NULL,
  `edge_exception_return_type` int(11) NOT NULL,
  `skip_flag` int(11) NOT NULL,
  `skip_value` varchar(100) DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`edge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `publish_flow` (
  `flow_id` varchar(50) NOT NULL,
  `category_id` varchar(50) NOT NULL,
  `flow_name` varchar(100) NOT NULL,
  `flow_desc` varchar(100) DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `publish_node` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `node_name` varchar(100) NOT NULL,
  `ref_name` varchar(100) NOT NULL,
  `node_type` int(11) NOT NULL,
  `start_type` int(11) NOT NULL,
  `execute_type` int(11) NOT NULL,
  `start_cron` varchar(100) DEFAULT NULL,
  `sub_flow_id` varchar(50) DEFAULT NULL,
  `sub_node_id` varchar(50) DEFAULT NULL,
  `layout_x` varchar(100) DEFAULT NULL,
  `layout_y` varchar(100) DEFAULT NULL,
  `skip_flag` int(11) NOT NULL,
  `skip_value` varchar(100) DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `publish_node_http` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `timeout` varchar(100) DEFAULT NULL,
  `timeout_type` int(11) NOT NULL,
  `success_code` varchar(100) NOT NULL,
  `error_type` int(11) NOT NULL,
  `sync_flag` int(11) NOT NULL,
  `http_url` varchar(1000) DEFAULT NULL,
  `http_method` varchar(100) DEFAULT NULL,
  `http_header` varchar(1000) DEFAULT NULL,
  `http_body` varchar(1000) DEFAULT NULL,
  `http_content_type` varchar(100) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `publish_node_roles` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `roles_id` varchar(50) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`,`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `publish_node_shell` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `timeout` varchar(100) DEFAULT NULL,
  `timeout_type` int(11) NOT NULL,
  `success_code` varchar(100) NOT NULL,
  `error_type` int(11) NOT NULL,
  `sync_flag` int(11) NOT NULL,
  `shell_location` varchar(1000) DEFAULT NULL,
  `shell_param` varchar(1000) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `history_edge` (
  `flow_id` varchar(50) NOT NULL,
  `edge_id` varchar(50) NOT NULL,
  `history_id` varchar(50) NOT NULL,
  `from_node_id` varchar(50) NOT NULL,
  `to_node_id` varchar(50) NOT NULL,
  `edge_condition` varchar(1000) DEFAULT NULL,
  `edge_exception_return_type` int(11) NOT NULL,
  `skip_flag` int(11) NOT NULL,
  `skip_value` varchar(100) DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`edge_id`,`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `history_flow` (
  `flow_id` varchar(50) NOT NULL,
  `history_id` varchar(50) NOT NULL,
  `category_id` varchar(50) NOT NULL,
  `flow_name` varchar(100) NOT NULL,
  `flow_desc` varchar(100) DEFAULT NULL,
  `flow_status` int(11) NOT NULL,
  `flow_result` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `error_time` datetime DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `history_node` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `history_id` varchar(50) NOT NULL,
  `node_name` varchar(100) NOT NULL,
  `ref_name` varchar(100) NOT NULL,
  `node_type` int(11) NOT NULL,
  `start_type` int(11) NOT NULL,
  `execute_type` int(11) NOT NULL,
  `start_cron` varchar(100) DEFAULT NULL,
  `sub_flow_id` varchar(50) DEFAULT NULL,
  `sub_node_id` varchar(50) DEFAULT NULL,
  `layout_x` varchar(100) DEFAULT NULL,
  `layout_y` varchar(100) DEFAULT NULL,
  `skip_flag` int(11) NOT NULL,
  `skip_value` varchar(100) DEFAULT NULL,
  `node_status` int(11) NOT NULL,
  `node_status_detail` int(11) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `node_result_message` varchar(100) DEFAULT NULL,
  `disable_flag` int(11) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`,`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `history_node_http` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `history_id` varchar(50) NOT NULL,
  `timeout` varchar(100) DEFAULT NULL,
  `timeout_type` int(11) NOT NULL,
  `success_code` varchar(100) NOT NULL,
  `error_type` int(11) NOT NULL,
  `sync_flag` int(11) NOT NULL,
  `http_url` varchar(1000) DEFAULT NULL,
  `http_method` varchar(100) DEFAULT NULL,
  `http_header` varchar(1000) DEFAULT NULL,
  `http_body` varchar(1000) DEFAULT NULL,
  `http_content_type` varchar(100) DEFAULT NULL,
  `http_request` varchar(1000) DEFAULT NULL,
  `http_res_status` varchar(100) DEFAULT NULL,
  `http_response` varchar(1000) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`,`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `history_node_roles` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `history_id` varchar(50) NOT NULL,
  `roles_id` varchar(50) NOT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`,`history_id`,`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `history_node_shell` (
  `flow_id` varchar(50) NOT NULL,
  `node_id` varchar(50) NOT NULL,
  `history_id` varchar(50) NOT NULL,
  `timeout` varchar(100) DEFAULT NULL,
  `timeout_type` int(11) NOT NULL,
  `success_code` varchar(100) NOT NULL,
  `error_type` int(11) NOT NULL,
  `sync_flag` int(11) NOT NULL,
  `shell_location` varchar(1000) DEFAULT NULL,
  `shell_param` varchar(1000) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `update_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`flow_id`,`node_id`,`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(400) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `permission_category` (
  `permission_id` int(11) NOT NULL,
  `category_id` varchar(100) NOT NULL,
  `can_read` int(11) NOT NULL,
  `can_update` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`permission_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `trole` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(400) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tuser` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(400) DEFAULT NULL,
  `email` varchar(400) DEFAULT NULL,
  `passwd` varchar(100) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `admin_flag` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO tuser (user_name,email,passwd,remarks,admin_flag,create_time,update_time) values ('admin','admin','admin','',1,now(),now());

INSERT INTO `category` (`id`, `category_name`, `category_type`, `parent`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES
('1', 'root', 'default', '#', 'sys', 'sys', '2022-06-03 23:15:27', '2022-06-03 23:15:27'),
('17b1fbef5b43b0', 'demo', 'default', '1', 'sys', 'sys', '2022-06-03 23:15:27', '2022-06-03 23:15:27'),
('18129f9dd4e64', 'demo', 'file', '17b1fbef5b43b0', 'admin', 'admin', '2022-06-03 23:30:49', '2022-06-03 23:30:49');

INSERT INTO `flow` (`flow_id`, `category_id`, `flow_name`, `flow_desc`, `publish_time`, `disable_flag`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES
('18129f9dd4e64', '18129f9dd4e64', 'demo', NULL, NULL, 0, 'admin', 'admin', '2022-06-03 23:30:49', '2022-06-03 23:30:49');

INSERT INTO `node` (`flow_id`, `node_id`, `node_name`, `ref_name`, `node_type`, `start_type`, `execute_type`, `start_cron`, `sub_flow_id`, `sub_node_id`, `layout_x`, `layout_y`, `skip_flag`, `skip_value`, `disable_flag`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES
('18129f9dd4e64', 'start', 'start', 'start', 1, 1, 1, NULL, NULL, NULL, '0', '0', 0, NULL, 0, 'admin', 'admin', '2022-06-03 23:30:49', '2022-06-03 23:30:49');

