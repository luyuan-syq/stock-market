/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50022
Source Host           : 127.0.0.1:3306
Source Database       : faff

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2017-01-03 07:43:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '用户的ID',
  `user_name` varchar(128) character set utf8 collate utf8_bin NOT NULL default '' COMMENT '用户的user_name',
  `password` varchar(32) NOT NULL default '' COMMENT '用户的登陆密码',
  `create_time` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '创建时间',
  `operator_id` varchar(32) default NULL COMMENT '操作人ID',
  `operator_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `first_recharge_time` timestamp NOT NULL default '1970-01-01 08:00:01' COMMENT '首次充值时间',
  `last_login_time` datetime default NULL COMMENT '最后登录时间',
  `contact_phone` varchar(32) default NULL COMMENT '单位电话（座机）',
  `mobile` varchar(32) default NULL COMMENT '手机',
  `contact_email` varchar(128) default NULL COMMENT '联系邮箱',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外事人员表';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('31', '张三', 'password', '2016-11-03 21:43:14', '100000', '2016-11-03 21:44:55', '1970-01-01 08:00:01', '2016-11-03 21:44:54', '010-1234567', '13552156563', 'weileni@163.com');
INSERT INTO `account` VALUES ('32', '李四', 'password', '2016-11-03 21:44:21', '100000', '2016-11-03 21:45:00', '1970-01-01 08:00:01', '2016-11-02 21:44:56', '010-1234567', '17603207557', 'sahoyuqi@qq.com');
INSERT INTO `account` VALUES ('33', '王五', 'password', '2016-11-03 21:44:38', '100000', '2016-11-03 21:45:06', '1970-01-01 08:00:01', '2016-11-01 21:45:01', '010-1234567', '13667236237', 'weileni@163.com');
INSERT INTO `account` VALUES ('34', '邵1', 'password', '2016-11-05 15:10:02', '100000', '2016-11-05 15:10:02', '1970-01-01 08:00:01', null, '010-1234567', '13667236237', 'sahoyuqi@qq.com');
INSERT INTO `account` VALUES ('35', '邵2', 'password', '2016-11-05 15:10:12', '100000', '2016-11-05 15:10:12', '1970-01-01 08:00:01', null, '010-1234567', '13667236237', 'weileni@163.com');
INSERT INTO `account` VALUES ('36', '邵3', 'password', '2016-11-05 15:10:25', '100000', '2016-11-05 15:10:25', '1970-01-01 08:00:01', null, '010-1234567', '13667236237', 'weileni@163.com');
INSERT INTO `account` VALUES ('37', '邵4', 'password', '2016-11-05 15:10:36', '100000', '2016-11-05 15:10:36', '1970-01-01 08:00:01', null, '010-1234567', '13667236237', 'weileni@163.com');
INSERT INTO `account` VALUES ('38', '邵5', 'password', '2016-11-05 15:10:47', '100000', '2016-11-05 15:10:47', '1970-01-01 08:00:01', null, '010-1234567', '13667236237', 'sahoyuqi@qq.com');
INSERT INTO `account` VALUES ('39', '邵6', 'password', '2016-11-05 15:11:01', '100000', '2016-11-05 15:11:01', '1970-01-01 08:00:01', null, '010-1234567', '17603207557', 'weileni@163.com');
INSERT INTO `account` VALUES ('40', '邵7', 'password', '2016-11-05 15:11:13', '100000', '2016-11-05 15:11:13', '1970-01-01 08:00:01', null, '010-1234567', '13667236237', 'sahoyuqi@qq.com');
INSERT INTO `account` VALUES ('41', '邵8', 'password', '2016-11-05 15:11:25', '100000', '2016-11-05 15:11:25', '1970-01-01 08:00:01', null, '010-1234567', '13667236237', 'weileni@163.com');

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` bigint(20) unsigned NOT NULL auto_increment COMMENT '唯一的自增主键',
  `operator_id` bigint(20) NOT NULL default '0' COMMENT '操作者id',
  `operator_name` varchar(128) NOT NULL default '' COMMENT '操作者账户名',
  `operator_role` varchar(200) NOT NULL default '' COMMENT '操作者当前对应角色',
  `target_subject` varchar(64) NOT NULL default '' COMMENT '被操作对象主体信息',
  `content` varchar(50) NOT NULL default '' COMMENT '操作内容，用来前端列表展示',
  `content_detail` varchar(100) NOT NULL default '' COMMENT '操作详细内容',
  `create_time` timestamp NOT NULL default '2000-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限系统日志表';

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', '1234', '邵玉琪', '超级管理员', '护照', '添加护照', '护照{id：1，name：ss}', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for `mail_info`
-- ----------------------------
DROP TABLE IF EXISTS `mail_info`;
CREATE TABLE `mail_info` (
  `id` bigint(20) unsigned NOT NULL auto_increment COMMENT '唯一的自增主键',
  `name` varchar(64) default NULL,
  `port` int(11) default NULL,
  `password` varchar(32) default NULL,
  `sender` varchar(255) default NULL,
  `isauth` bit(1) default '\0',
  `server_addr` varchar(120) default NULL,
  `user_name` varchar(64) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_info
-- ----------------------------
INSERT INTO `mail_info` VALUES ('1', 's', '2', '3', '3', '', '3', '3');

-- ----------------------------
-- Table structure for `passport`
-- ----------------------------
DROP TABLE IF EXISTS `passport`;
CREATE TABLE `passport` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '护照的ID',
  `task_id` bigint(20) NOT NULL default '0' COMMENT '任务的ID',
  `task_code` varchar(32) NOT NULL default '' COMMENT '任务编码',
  `name` varchar(64) NOT NULL default '' COMMENT '名字',
  `surname` varchar(32) NOT NULL default '' COMMENT '姓',
  `given_names` varchar(64) NOT NULL default '' COMMENT '名',
  `passport_no` varchar(64) NOT NULL default '' COMMENT '护照编码',
  `passport_type` int(11) NOT NULL default '0' COMMENT '护照类型',
  `country_code` varchar(64) NOT NULL default '' COMMENT '国家编码',
  `sex` int(11) NOT NULL default '0' COMMENT '性别：0 男，1 女',
  `data_birth` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '出生年月',
  `place_birth` varchar(128) NOT NULL default '' COMMENT '出生地',
  `place_issue` varchar(128) NOT NULL default '' COMMENT '颁发机构',
  `date_issue` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '颁发日期',
  `date_expire` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '过期日期',
  `anthority` varchar(32) NOT NULL default '' COMMENT '权威',
  `id_number` varchar(32) NOT NULL default '' COMMENT '身份证号',
  `passport_status` int(11) NOT NULL default '0' COMMENT '护照状态: ',
  `status` int(11) NOT NULL default '0' COMMENT '状态: 未办理0，待审核1，审核未通过2，办理中3，归档4',
  `create_time` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '创建时间',
  `operator_id` varchar(32) default NULL COMMENT '操作人ID',
  `operator_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外事任务表';

-- ----------------------------
-- Records of passport
-- ----------------------------
INSERT INTO `passport` VALUES ('2', '1', '100001', '邵玉琪', '邵', '玉琪', '123232322', '1', 'american', '0', '1970-01-01 08:00:01', '山东省沂水县邵家宅村', '山东省沂水县公安局', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '1', '371323199007204038', '0', '0', '1970-01-01 08:00:01', 'liuqing', '2016-11-03 22:11:24');
INSERT INTO `passport` VALUES ('3', '1', '100002', '李小可', '李', '小可', '1232325555', '1', 'american', '0', '1970-01-01 08:00:01', '山东省沂水县邵家宅村', '山东省沂水县公安局', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '1', '371323199007204038', '0', '1', '1970-01-01 08:00:01', 'liuqing', '2016-12-25 22:10:10');
INSERT INTO `passport` VALUES ('4', '1', '100002', '刘旭', '刘', '旭', '1232323444', '1', 'american', '0', '1970-01-01 08:00:01', '山东省沂水县邵家宅村', '山东省沂水县公安局', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '1', '371323199007204038', '0', '2', '1970-01-01 08:00:01', 'liuqing', '2016-12-25 22:10:12');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(64) default NULL,
  `description` varchar(64) default NULL,
  `create_time` datetime NOT NULL default '2010-01-01 08:00:01' COMMENT '创建时间',
  `operator_id` varchar(32) default NULL COMMENT '操作人ID',
  `operator_name` varchar(32) default NULL COMMENT '操作人ID',
  `operator_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '此角色拥有所有权限', '2010-01-01 08:00:01', '1', '邵玉琪', '2016-12-26 22:58:55');

-- ----------------------------
-- Table structure for `role_and_right`
-- ----------------------------
DROP TABLE IF EXISTS `role_and_right`;
CREATE TABLE `role_and_right` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '用户的ID',
  `right_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_and_right
-- ----------------------------

-- ----------------------------
-- Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '用户的ID',
  `task_name` varchar(128) NOT NULL default '' COMMENT '任务名称',
  `header_name` varchar(32) NOT NULL default '' COMMENT '团长姓名',
  `header_idcard` varchar(32) NOT NULL default '' COMMENT '团长身份证号',
  `task_begin_time` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '任务开始时间',
  `task_end_time` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '任务结束时间',
  `operator_id` varchar(32) default NULL COMMENT '操作人ID',
  `operator_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `task_description` varchar(1024) NOT NULL default '' COMMENT '任务描述',
  `task_country` varchar(512) NOT NULL default '' COMMENT '前往国家，支持多个',
  `status` int(11) NOT NULL default '-1' COMMENT '-1 新建',
  `task_code` varchar(32) NOT NULL default '' COMMENT '任务编码',
  `create_time` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外事任务表';

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('1', '加拿大出差', '邵玉琪', '371323199007204038', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '100000', '2016-11-05 12:49:36', '打算', '美国，泰国', '-1', 'happy', '1970-01-01 08:00:01');
INSERT INTO `task` VALUES ('2', '韩国出差', '邵玉琪', '371323199007204038', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '100000', '2016-11-03 22:01:44', '打算', '', '-1', 'happy', '1970-01-01 08:00:01');
INSERT INTO `task` VALUES ('5', '日本出差', '邵玉琪', '371323199007204038', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '100000', '2016-11-03 22:01:41', '打算', '', '-1', 'happy', '1970-01-01 08:00:01');
INSERT INTO `task` VALUES ('7', '我要去德国', '邵玉琪', '371323199007204038', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '100000', '2016-11-03 22:01:39', '打算', '', '-1', 'happy', '1970-01-01 08:00:01');
INSERT INTO `task` VALUES ('8', '去美国happy', '邵玉琪', '371323199007204038', '1970-01-01 08:00:01', '1970-01-01 08:00:01', '100000', '2016-11-03 21:50:11', '打算', '', '-1', 'happy', '1970-01-01 08:00:01');
INSERT INTO `task` VALUES ('9', '回家探亲', 'shaoyuqi', '371323199007204038', '2016-12-24 16:18:30', '2016-12-31 16:18:30', '100000', '2016-12-24 16:19:11', '特别想家了', 'hubei,shaanxi', '-1', 'cv508g', '2016-12-24 16:19:11');

-- ----------------------------
-- Table structure for `task_account`
-- ----------------------------
DROP TABLE IF EXISTS `task_account`;
CREATE TABLE `task_account` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键ID',
  `task_id` bigint(20) NOT NULL COMMENT '任务主键',
  `account_id` bigint(20) NOT NULL COMMENT '外事人员主键',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务外事人员中间表';

-- ----------------------------
-- Records of task_account
-- ----------------------------
INSERT INTO `task_account` VALUES ('66', '1', '32');

-- ----------------------------
-- Table structure for `task_code`
-- ----------------------------
DROP TABLE IF EXISTS `task_code`;
CREATE TABLE `task_code` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '任务编码的ID',
  `task_code` varchar(128) character set utf8 collate utf8_bin NOT NULL default '' COMMENT '任务编码',
  `create_time` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '创建时间',
  `operator_id` varchar(32) default NULL COMMENT '操作人ID',
  `operator_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `status` int(11) NOT NULL default '1' COMMENT '任务编码状态: 1 未分配，2 在用，3 已关闭',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `task_code` (`task_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务编码表';

-- ----------------------------
-- Records of task_code
-- ----------------------------
INSERT INTO `task_code` VALUES ('40', 'x3jkko', '2016-10-07 11:53:49', 'shaoyuqi', '2016-10-07 11:53:49', '1');
INSERT INTO `task_code` VALUES ('41', 'wdfe5y', '2016-10-07 11:53:49', 'shaoyuqi', '2016-10-07 11:53:49', '1');
INSERT INTO `task_code` VALUES ('42', 'cv508g', '2016-10-07 12:05:23', 'shaoyuqi', '2016-10-07 12:05:23', '1');
INSERT INTO `task_code` VALUES ('43', 'ksncg2', '2016-10-07 12:05:23', 'shaoyuqi', '2016-10-07 12:05:23', '1');
INSERT INTO `task_code` VALUES ('44', 'bhvpt5', '2016-10-07 12:05:23', 'shaoyuqi', '2016-10-07 12:05:23', '1');
INSERT INTO `task_code` VALUES ('45', 'ea4dlv', '2016-10-07 12:05:23', 'shaoyuqi', '2016-10-07 12:05:23', '1');
INSERT INTO `task_code` VALUES ('46', 'v7iix3', '2016-10-07 21:15:35', 'shaoyuqi', '2016-10-07 21:15:35', '1');
INSERT INTO `task_code` VALUES ('47', 'stifno', '2016-10-07 21:15:40', 'shaoyuqi', '2016-10-07 21:15:40', '1');
INSERT INTO `task_code` VALUES ('48', 'm660og', '2016-10-07 21:15:41', 'shaoyuqi', '2016-10-07 21:15:41', '1');
INSERT INTO `task_code` VALUES ('49', 'a7wawn', '2016-10-07 21:15:41', 'shaoyuqi', '2016-10-07 21:15:41', '1');
INSERT INTO `task_code` VALUES ('50', 'n4j2mf', '2016-10-07 21:15:59', 'shaoyuqi', '2016-10-07 21:15:59', '1');
INSERT INTO `task_code` VALUES ('51', 'yzekyu', '2016-10-07 21:16:07', 'shaoyuqi', '2016-10-07 21:16:07', '1');
INSERT INTO `task_code` VALUES ('52', 'q4kk4l', '2016-10-07 21:54:31', 'shaoyuqi', '2016-10-07 21:54:31', '1');
INSERT INTO `task_code` VALUES ('53', 'vzbq5l', '2016-10-07 22:01:34', 'shaoyuqi', '2016-10-07 22:01:34', '1');
INSERT INTO `task_code` VALUES ('54', '2ho0nc', '2016-11-03 21:41:25', 'shaoyuqi', '2016-11-03 21:41:25', '1');
INSERT INTO `task_code` VALUES ('55', 'ppihmm', '2016-11-03 21:41:26', 'shaoyuqi', '2016-11-03 21:41:26', '1');
INSERT INTO `task_code` VALUES ('56', 'tgo6ij', '2016-11-03 21:41:27', 'shaoyuqi', '2016-11-03 21:41:27', '1');
INSERT INTO `task_code` VALUES ('57', '5alhi5', '2016-11-05 10:34:35', 'shaoyuqi', '2016-11-05 10:34:35', '1');
INSERT INTO `task_code` VALUES ('58', '9yvxet', '2016-11-05 10:34:35', 'shaoyuqi', '2016-11-05 10:34:35', '1');
INSERT INTO `task_code` VALUES ('59', 's0yy1i', '2016-11-05 10:34:48', 'shaoyuqi', '2016-11-05 10:34:48', '1');
INSERT INTO `task_code` VALUES ('60', 'lt6va4', '2016-11-05 10:34:48', 'shaoyuqi', '2016-11-05 10:34:48', '1');
INSERT INTO `task_code` VALUES ('61', '3jf2dl', '2016-11-05 10:34:48', 'shaoyuqi', '2016-11-05 10:34:48', '1');
INSERT INTO `task_code` VALUES ('62', 'kulb2q', '2016-11-05 10:34:48', 'shaoyuqi', '2016-11-05 10:34:48', '1');
INSERT INTO `task_code` VALUES ('63', '93sdus', '2016-11-05 10:45:14', 'shaoyuqi', '2016-11-05 10:45:14', '1');
INSERT INTO `task_code` VALUES ('64', 'oie18f', '2016-11-05 10:58:15', 'shaoyuqi', '2016-11-05 10:58:15', '1');
INSERT INTO `task_code` VALUES ('65', 'vbef40', '2016-11-05 10:58:16', 'shaoyuqi', '2016-11-05 10:58:16', '1');
INSERT INTO `task_code` VALUES ('66', '8ilkbd', '2016-11-05 10:58:16', 'shaoyuqi', '2016-11-05 10:58:16', '1');
INSERT INTO `task_code` VALUES ('67', 'ghahay', '2016-11-05 10:59:32', 'shaoyuqi', '2016-11-05 10:59:32', '1');
INSERT INTO `task_code` VALUES ('68', 'pah9kf', '2016-11-05 11:01:16', 'shaoyuqi', '2016-11-05 11:01:16', '1');
INSERT INTO `task_code` VALUES ('69', 'igt4uw', '2016-11-05 11:04:54', 'shaoyuqi', '2016-11-05 11:04:54', '1');
INSERT INTO `task_code` VALUES ('70', 'hmkf48', '2016-11-05 11:05:35', 'shaoyuqi', '2016-11-05 11:05:35', '1');
INSERT INTO `task_code` VALUES ('71', 'fiomyg', '2016-11-05 11:06:39', 'shaoyuqi', '2016-11-05 11:06:39', '1');
INSERT INTO `task_code` VALUES ('72', 'k17i7t', '2016-11-05 11:16:03', 'shaoyuqi', '2016-11-05 11:16:03', '1');
INSERT INTO `task_code` VALUES ('73', 'm8dbix', '2016-11-05 11:17:33', 'shaoyuqi', '2016-11-05 11:17:33', '1');
INSERT INTO `task_code` VALUES ('74', 'vlbued', '2016-11-05 15:12:50', 'shaoyuqi', '2016-11-05 15:12:50', '1');

-- ----------------------------
-- Table structure for `visa_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `visa_attribute`;
CREATE TABLE `visa_attribute` (
  `aid` bigint(20) NOT NULL auto_increment,
  `principal_id` bigint(20) NOT NULL,
  `attr_name` varchar(200) NOT NULL,
  `attr_value` varchar(1000) default NULL,
  PRIMARY KEY  (`aid`,`principal_id`,`attr_name`),
  KEY `ix_name_lookup` (`attr_name`),
  KEY `ix_principal_attr` (`principal_id`),
  CONSTRAINT `ix_principal_attr` FOREIGN KEY (`principal_id`) REFERENCES `visa_principal` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visa_attribute
-- ----------------------------
INSERT INTO `visa_attribute` VALUES ('1', '1', 'name', '元含');
INSERT INTO `visa_attribute` VALUES ('2', '1', 'principal.label', 'sdfs');

-- ----------------------------
-- Table structure for `visa_country`
-- ----------------------------
DROP TABLE IF EXISTS `visa_country`;
CREATE TABLE `visa_country` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `name` varchar(128) NOT NULL default '' COMMENT '国家名称',
  `parent_id` bigint(32) default '0' COMMENT '父节点',
  `code` varchar(128) NOT NULL default '' COMMENT '国家编码',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签证国家表';

-- ----------------------------
-- Records of visa_country
-- ----------------------------
INSERT INTO `visa_country` VALUES ('1', '中国', '0', 'china');
INSERT INTO `visa_country` VALUES ('2', '美国', '0', 'sds');
INSERT INTO `visa_country` VALUES ('3', '德国12', '0', 'wee');
INSERT INTO `visa_country` VALUES ('13', 'sdf', '0', '');

-- ----------------------------
-- Table structure for `visa_principal`
-- ----------------------------
DROP TABLE IF EXISTS `visa_principal`;
CREATE TABLE `visa_principal` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `type` smallint(6) NOT NULL default '0' COMMENT '签证类型',
  `template_code` varchar(20) NOT NULL default '' COMMENT '签证模板',
  `create_time` datetime NOT NULL default '1970-01-01 08:00:01' COMMENT '创建时间',
  `operator_id` varchar(32) default NULL COMMENT '操作人ID',
  `operator_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `status` int(11) NOT NULL default '-1' COMMENT '签证状态',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签证表';

-- ----------------------------
-- Records of visa_principal
-- ----------------------------
INSERT INTO `visa_principal` VALUES ('1', '0', 'china', '1970-01-01 08:00:01', '10000', '2016-10-05 08:59:30', '-1');

-- ----------------------------
-- Table structure for `wzax_module`
-- ----------------------------
DROP TABLE IF EXISTS `wzax_module`;
CREATE TABLE `wzax_module` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '自增主键',
  `name` varchar(50) default NULL COMMENT '模块名称',
  `uuid` varchar(50) default NULL COMMENT '模块唯一标记',
  `uuname` varchar(50) default NULL COMMENT '唯一名称',
  `url` varchar(50) default NULL COMMENT '资源URL',
  `parent_id` bigint(20) default NULL COMMENT '父节点',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限模块表';

-- ----------------------------
-- Records of wzax_module
-- ----------------------------
INSERT INTO `wzax_module` VALUES ('1', '一级模块', '1111', '一级模块', null, '0');
INSERT INTO `wzax_module` VALUES ('2', '二级模块', '2222', '二级模块', null, '1');

-- ----------------------------
-- Table structure for `wzax_right`
-- ----------------------------
DROP TABLE IF EXISTS `wzax_right`;
CREATE TABLE `wzax_right` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '自增主键',
  `name` varchar(20) default NULL COMMENT '名称',
  `url` varchar(50) default NULL COMMENT '资源URL',
  `uuid` varchar(50) default NULL COMMENT '唯一标记',
  `module_id` bigint(20) default NULL COMMENT '模块ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of wzax_right
-- ----------------------------
INSERT INTO `wzax_right` VALUES ('1', '二菜单', '/url', '3333', '2');
INSERT INTO `wzax_right` VALUES ('2', '二菜单2', '/url', '22222', '2');
