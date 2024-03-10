/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50732
Source Host           : 127.0.0.1:3306
Source Database       : smilex-boot

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2024-03-10 15:30:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `app_upgrade`;
CREATE TABLE `app_upgrade` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `version_num` smallint(5) NOT NULL COMMENT 'APP版本号数字，例如101',
  `version_show` varchar(50) NOT NULL COMMENT 'APP版本号展示，例如1.0.1',
  `version_desc` varchar(1000) NOT NULL COMMENT '版本说明',
  `app_size` double NOT NULL COMMENT '包大小',
  `upgrade_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否需要更新，0无需更新，1需要更新，2需要且强制更新',
  `min_version` smallint(5) DEFAULT NULL COMMENT '最小APP版本号数字，低于该版本强制升级，例101',
  `download_url` varchar(255) NOT NULL COMMENT '下载地址',
  `audit_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '应用商店是否审核通过，0未通过，1已通过',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='APP版本信息';

-- ----------------------------
-- Records of app_upgrade
-- ----------------------------

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `section_id` bigint(20) NOT NULL COMMENT '栏目ID',
  `tag_ids` varchar(255) NOT NULL COMMENT '标签id，以,分割',
  `tag_names` varchar(100) NOT NULL COMMENT '标签名称，以，分割',
  `poster` varchar(255) DEFAULT NULL COMMENT '封面图',
  `article_title` varchar(255) NOT NULL COMMENT '标题',
  `article_digest` varchar(100) NOT NULL COMMENT '文章简介',
  `article_content` text NOT NULL COMMENT '文章内容',
  `grammar_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '语法类型，1markdown，2html',
  `visit_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '访问类型,1通用类型，2统一密码，3独立密码',
  `password` varchar(255) DEFAULT NULL COMMENT '独立密码',
  `salt` varchar(50) DEFAULT NULL COMMENT 'salt',
  `publish_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发布状态，0未发布，1已发布',
  `top_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否置顶排行版，0否，1是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户博客文章';

-- ----------------------------
-- Records of blog_article
-- ----------------------------

-- ----------------------------
-- Table structure for blog_article_draft
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_draft`;
CREATE TABLE `blog_article_draft` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `article_id` bigint(20) DEFAULT NULL COMMENT '文章ID',
  `poster` varchar(255) DEFAULT NULL COMMENT '封面图',
  `article_title` varchar(255) NOT NULL COMMENT '文章标题',
  `article_digest` varchar(100) NOT NULL COMMENT '文章简介',
  `article_content` text NOT NULL COMMENT '文章内容',
  `publish_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '发布状态，0未发布，1已发布',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户博客草稿箱';

-- ----------------------------
-- Records of blog_article_draft
-- ----------------------------

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `open_user_id` bigint(20) DEFAULT NULL COMMENT '开放用户ID',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户博客评论';

-- ----------------------------
-- Records of blog_comment
-- ----------------------------

-- ----------------------------
-- Table structure for blog_git_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_git_article`;
CREATE TABLE `blog_git_article` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `blog_article_id` bigint(20) DEFAULT NULL COMMENT '博客文章ID',
  `file_title` varchar(255) DEFAULT NULL COMMENT '文件标题',
  `content_url` varchar(500) DEFAULT NULL COMMENT '内容URL',
  `content_text` mediumtext COMMENT '内容文本',
  `update_flag` tinyint(1) DEFAULT '0' COMMENT '是否更新，1是，0否',
  `publish_flag` tinyint(1) DEFAULT '0' COMMENT '是否发布，0未发布，1已发布',
  `async_time` datetime DEFAULT NULL COMMENT '同步时间',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户博客-git文章同步';

-- ----------------------------
-- Records of blog_git_article
-- ----------------------------

-- ----------------------------
-- Table structure for blog_section
-- ----------------------------
DROP TABLE IF EXISTS `blog_section`;
CREATE TABLE `blog_section` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父ID,最上级为0',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `section_name` varchar(255) NOT NULL COMMENT '栏目名称',
  `level` smallint(1) NOT NULL DEFAULT '1' COMMENT '层级',
  `visit_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '访问类型，1无限制，2统一密码访问',
  `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '栏目类型，1板块，2分组，3路由',
  `route_url` varchar(255) DEFAULT NULL COMMENT '路由URL',
  `order_num` smallint(5) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户博客栏目';

-- ----------------------------
-- Records of blog_section
-- ----------------------------

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `tag_name` varchar(100) DEFAULT NULL COMMENT '标签名',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '是否启用，0禁用1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户博客标签';

-- ----------------------------
-- Records of blog_tag
-- ----------------------------

-- ----------------------------
-- Table structure for blog_timeline
-- ----------------------------
DROP TABLE IF EXISTS `blog_timeline`;
CREATE TABLE `blog_timeline` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `year` char(4) NOT NULL COMMENT '年份',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(1000) NOT NULL COMMENT '说明',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='博客时间线';

-- ----------------------------
-- Records of blog_timeline
-- ----------------------------
INSERT INTO `blog_timeline` VALUES ('318466839598333951', '297516356822106112', '2021', 'demo', '当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春', '2022-08-27 11:58:00', 'admin', '2022-08-29 15:25:21', 'admin', '0');
INSERT INTO `blog_timeline` VALUES ('318466839598333952', '297516356822106112', '2022', 'demo', '当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春', '2022-08-27 11:58:00', 'admin', '2022-08-29 15:25:21', 'admin', '0');
INSERT INTO `blog_timeline` VALUES ('318466839598333953', '297516356822106112', '2023', 'demo', '当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春当你老了，头发白了，炉火旁打盹，回忆青春', '2022-08-27 11:58:00', 'admin', '2022-08-29 15:25:21', 'admin', '0');

-- ----------------------------
-- Table structure for open_user
-- ----------------------------
DROP TABLE IF EXISTS `open_user`;
CREATE TABLE `open_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '来源租户ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '盐值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='开放用户管理';

-- ----------------------------
-- Records of open_user
-- ----------------------------

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='序列表';

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('1');
INSERT INTO `sequence` VALUES ('2');
INSERT INTO `sequence` VALUES ('3');
INSERT INTO `sequence` VALUES ('4');
INSERT INTO `sequence` VALUES ('5');
INSERT INTO `sequence` VALUES ('6');
INSERT INTO `sequence` VALUES ('7');
INSERT INTO `sequence` VALUES ('8');
INSERT INTO `sequence` VALUES ('9');
INSERT INTO `sequence` VALUES ('10');
INSERT INTO `sequence` VALUES ('11');
INSERT INTO `sequence` VALUES ('12');
INSERT INTO `sequence` VALUES ('13');
INSERT INTO `sequence` VALUES ('14');
INSERT INTO `sequence` VALUES ('15');
INSERT INTO `sequence` VALUES ('16');
INSERT INTO `sequence` VALUES ('17');
INSERT INTO `sequence` VALUES ('18');
INSERT INTO `sequence` VALUES ('19');
INSERT INTO `sequence` VALUES ('20');
INSERT INTO `sequence` VALUES ('21');
INSERT INTO `sequence` VALUES ('22');
INSERT INTO `sequence` VALUES ('23');
INSERT INTO `sequence` VALUES ('24');
INSERT INTO `sequence` VALUES ('25');
INSERT INTO `sequence` VALUES ('26');
INSERT INTO `sequence` VALUES ('27');
INSERT INTO `sequence` VALUES ('28');
INSERT INTO `sequence` VALUES ('29');
INSERT INTO `sequence` VALUES ('30');
INSERT INTO `sequence` VALUES ('31');
INSERT INTO `sequence` VALUES ('32');
INSERT INTO `sequence` VALUES ('33');
INSERT INTO `sequence` VALUES ('34');
INSERT INTO `sequence` VALUES ('35');
INSERT INTO `sequence` VALUES ('36');
INSERT INTO `sequence` VALUES ('37');
INSERT INTO `sequence` VALUES ('38');
INSERT INTO `sequence` VALUES ('39');
INSERT INTO `sequence` VALUES ('40');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `config_name` varchar(255) NOT NULL COMMENT '配置名称',
  `config_key` varchar(255) NOT NULL COMMENT '配置key',
  `config_type` tinyint(2) NOT NULL COMMENT '配置类型，1text,2json',
  `config_value` text NOT NULL COMMENT '配置信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '是否启用，0启用1禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统配置';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `parent_id` bigint(20) NOT NULL COMMENT '父级部门，不存在则为0',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dict_code` varchar(50) NOT NULL COMMENT '字典编码',
  `dict_name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('297874820157145088', 'enableFlag', '启用状态', '0未启用，1启用', '2022-07-01 16:12:40', 'admin', '2022-07-01 16:12:40', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('297892445075537920', 'upgradeType', 'APP包更新类型', '是否需要更新，0无需更新，1需要更新，2需要且强制更新', '2022-07-01 17:22:42', 'admin', '2022-07-01 17:22:42', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('297893233105567744', 'sysMenuType', '系统菜单类型', '菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)', '2022-07-01 17:25:50', 'admin', '2022-07-02 09:28:15', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('298869120441516032', 'blogVisitType', '博客访问类型', '博客访问类型：1通用类型，2统一密码，3独立密码', '2022-07-04 10:03:40', 'admin', '2022-08-06 16:59:46', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('308376560618962944', 'logType', '日志类型', '1:登录日志;2:操作日志;3:定时任务;4:异常日志;', '2022-07-30 15:42:50', 'admin', '2022-07-30 15:42:50', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('308376657037623296', 'logOperateType', '日志操作类型', '1查询，2添加，3修改，4删除，5导入，6导出', '2022-07-30 15:43:13', 'admin', '2022-07-30 15:43:13', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('309038187844468736', 'logModule', '日志模块', 'sys:系统模块;blog:博客模块', '2022-08-01 11:31:55', 'admin', '2022-08-01 11:31:55', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('310931621354143744', 'blogSectionVisitType', '博客栏目访问类型', '博客栏目访问类型，1通用类型，2统一密码', '2022-08-06 16:55:44', 'admin', '2022-08-06 17:00:01', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('310934578296520704', 'blogGrammarType', '博客语法类型', '语法类型，1markdown，2html', '2022-08-06 17:07:29', 'admin', '2022-08-06 17:07:29', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('310934681941966848', 'blogPublishFlag', '博客发布状态', '发布状态，0未发布，1已发布', '2022-08-06 17:07:54', 'admin', '2022-08-06 17:07:54', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('310934681941966849', 'blogTopFlag', '博客置顶状态', '置顶状态，0未置顶，1已置顶', '2022-08-06 17:07:54', 'admin', '2022-08-06 17:07:54', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('310934681941966850', 'commonYN', '通用-是否', '0否，1是', '2022-08-06 17:07:54', 'admin', '2022-08-06 17:07:54', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('310934681941966851', 'blogSectionType', '博客栏目-类型', '1板块，2分组，3路由', '2022-08-06 17:07:54', 'admin', '2022-08-06 17:07:54', 'admin', '0');
INSERT INTO `sys_dict` VALUES ('439494462704451584', 'tempCode', 'tempName1', '临时测试', '2023-07-27 11:18:34', 'AUTO', '2023-07-27 11:18:34', 'AUTO', '0');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dict_id` bigint(20) DEFAULT NULL COMMENT '字典ID',
  `dict_code` varchar(50) DEFAULT NULL COMMENT '字典编码',
  `dict_value` varchar(50) DEFAULT NULL COMMENT '数据值',
  `dict_label` varchar(50) DEFAULT NULL COMMENT '数据显示项',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据字典信息';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('297888703869616128', '297874820157145100', 'enableFlag', '0', '禁用', null, '2022-07-01 17:07:50', 'admin', '2022-07-01 17:07:50', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('297888758722723840', '297874820157145100', 'enableFlag', '1', '启用', null, '2022-07-01 17:08:03', 'admin', '2022-07-01 17:10:38', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('297892666132135936', '297892445075537900', 'upgradeType', '0', '不更新', null, '2022-07-01 17:23:35', 'admin', '2022-07-01 17:23:35', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('297892786697404416', '297892445075537920', 'upgradeType', '1', '需要更新', null, '2022-07-01 17:24:04', 'admin', '2022-07-01 17:24:04', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('297892850715066368', '297892445075537920', 'upgradeType', '2', '强制更新', null, '2022-07-01 17:24:19', 'admin', '2022-07-01 17:24:19', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('298208819480100864', '297893233105567744', 'sysMenuType', '0', '菜单组', null, '2022-07-02 14:19:52', 'admin', '2022-07-02 14:19:52', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('298208936132083712', '297893233105567744', 'sysMenuType', '1', '菜单', null, '2022-07-02 14:20:20', 'admin', '2022-07-02 14:20:20', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('298208989949198336', '297893233105567744', 'sysMenuType', '2', '按钮权限', null, '2022-07-02 14:20:33', 'admin', '2022-07-02 14:20:33', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('298869256689287168', '298869120441516032', 'blogVisitType', '1', '不限制', null, '2022-07-04 10:04:12', 'admin', '2022-07-04 10:04:12', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('298869347680518144', '298869120441516032', 'blogVisitType', '2', '统一密码', null, '2022-07-04 10:04:34', 'admin', '2022-07-04 10:04:34', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('298869455021146112', '298869120441516032', 'blogVisitType', '3', '独立密码', null, '2022-07-04 10:05:00', 'admin', '2022-07-04 10:05:00', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308376929742880768', '308376560618962944', 'logType', '1', '登录日志', null, '2022-07-30 15:44:18', 'admin', '2022-07-30 15:44:18', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308394842570883072', '308376560618962944', 'logType', '2', '操作日志', null, '2022-07-30 16:55:29', 'admin', '2022-07-30 16:55:29', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395219437486080', '308376560618962944', 'logType', '3', '定时任务', null, '2022-07-30 16:56:59', 'admin', '2022-07-30 16:56:59', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395266589851648', '308376560618962944', 'logType', '4', '异常日志', null, '2022-07-30 16:57:10', 'admin', '2022-07-30 16:57:10', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395314757238784', '308376657037623296', 'logOperateType', '1', '查询', null, '2022-07-30 16:57:22', 'admin', '2022-07-30 16:57:22', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395351507730432', '308376657037623296', 'logOperateType', '2', '添加', null, '2022-07-30 16:57:30', 'admin', '2022-07-30 16:57:30', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395511696588800', '308376657037623296', 'logOperateType', '3', '修改', null, '2022-07-30 16:58:09', 'admin', '2022-07-30 16:58:09', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395558630850560', '308376657037623296', 'logOperateType', '4', '删除', null, '2022-07-30 16:58:20', 'admin', '2022-07-30 16:58:20', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395593590374400', '308376657037623296', 'logOperateType', '5', '导入', null, '2022-07-30 16:58:28', 'admin', '2022-07-30 16:58:28', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('308395634266734592', '308376657037623296', 'logOperateType', '6', '导出', null, '2022-07-30 16:58:38', 'admin', '2022-07-30 16:58:38', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('309038251849547776', '309038187844468736', 'logModule', 'SYS', '系统模块', null, '2022-08-01 11:32:10', 'admin', '2022-08-01 11:33:25', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('309038540849676288', '309038187844468736', 'logModule', 'BLOG', '博客模块', null, '2022-08-01 11:33:19', 'admin', '2022-08-01 11:33:19', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310931736915607552', '310931621354143744', 'blogSectionVisitType', '1', '无限制', null, '2022-08-06 16:56:12', 'admin', '2022-08-06 16:56:12', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310931795988185088', '310931621354143744', 'blogSectionVisitType', '2', '统一密码', null, '2022-08-06 16:56:26', 'admin', '2022-08-06 16:56:26', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934836271382528', '310934578296520704', 'blogGrammarType', '1', 'Markdown', null, '2022-08-06 17:08:31', 'admin', '2022-08-06 17:08:31', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934885223104512', '310934578296520704', 'blogGrammarType', '2', 'Html', null, '2022-08-06 17:08:42', 'admin', '2022-08-06 17:08:42', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934919377321984', '310934681941966848', 'blogPublishFlag', '0', '未发布', null, '2022-08-06 17:08:51', 'admin', '2022-08-06 17:08:51', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999872', '310934681941966848', 'blogPublishFlag', '1', '已发布', null, '2022-08-06 17:08:57', 'admin', '2022-08-06 17:08:57', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999881', '310934681941966849', 'blogTopFlag', '0', '未置顶', null, '2022-08-06 17:08:51', 'admin', '2022-08-06 17:08:51', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999882', '310934681941966849', 'blogTopFlag', '1', '已置顶', null, '2022-08-06 17:08:57', 'admin', '2022-08-06 17:08:57', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999883', '310934681941966850', 'commonYN', '0', '否', null, '2022-08-06 17:08:51', 'admin', '2022-08-06 17:08:51', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999884', '310934681941966850', 'commonYN', '1', '是', null, '2022-08-06 17:08:57', 'admin', '2022-08-06 17:08:57', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999885', '310934681941966851', 'blogSectionType', '1', '板块', null, '2022-08-06 17:08:51', 'admin', '2022-08-06 17:08:51', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999886', '310934681941966851', 'blogSectionType', '2', '分组', null, '2022-08-06 17:08:57', 'admin', '2022-08-06 17:08:57', 'admin', '0');
INSERT INTO `sys_dict_item` VALUES ('310934946417999887', '310934681941966851', 'blogSectionType', '3', '路由', null, '2022-08-06 17:08:57', 'admin', '2022-08-06 17:08:57', 'admin', '0');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `modules` varchar(50) NOT NULL COMMENT '模块，alioss,minio',
  `net_path` varchar(255) NOT NULL COMMENT '网络地址',
  `file_path` varchar(255) NOT NULL COMMENT '文件地址',
  `file_size` double NOT NULL COMMENT '文件大小',
  `file_suffix` varchar(50) NOT NULL COMMENT '文件后缀',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文件管理';

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `log_module` varchar(50) NOT NULL COMMENT '日志模块，sys,blog',
  `log_title` varchar(50) NOT NULL COMMENT '日志标题',
  `log_value` varchar(50) NOT NULL COMMENT '日志内容',
  `log_type` tinyint(2) NOT NULL COMMENT '日志类型1:登录日志;2:操作日志;3:定时任务;4:异常日志;',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `operate_type` tinyint(2) NOT NULL COMMENT '操作类型',
  `ip_address` varchar(100) DEFAULT NULL COMMENT 'IP地址',
  `method` varchar(500) DEFAULT NULL COMMENT '请求方法',
  `request_url` varchar(50) DEFAULT NULL COMMENT '请求url路径',
  `request_type` varchar(50) DEFAULT NULL COMMENT '请求类型',
  `request_params` text COMMENT '请求参数',
  `cost_time` int(11) DEFAULT NULL COMMENT '耗费时间',
  `err_msg` varchar(1000) DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('421089654184345600', 'SYS', '租户博客文章', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogArticleController.list', null, 'GET', '{\"articleTitle\":[\"\"],\"grammarType\":[\"\"],\"size\":[\"10\"],\"topFlag\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"sectionId\":[\"\"],\"publishFlag\":[\"\"],\"visitType\":[\"\"]}', '35', null, '2023-06-06 16:24:26', 'admin', '2023-06-06 16:24:26', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089655958536192', 'SYS', '租户博客评论', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogCommentController.list', null, 'GET', '{\"size\":[\"10\"],\"id\":[\"\"],\"page\":[\"1\"],\"openUserId\":[\"\"]}', '27', null, '2023-06-06 16:24:26', 'admin', '2023-06-06 16:24:26', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089659712438272', 'SYS', '租户博客栏目', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogSectionController.list', null, 'GET', '{\"sectionName\":[\"\"],\"size\":[\"10\"],\"id\":[\"\"],\"page\":[\"1\"],\"parentId\":[\"\"],\"visitType\":[\"\"]}', '13', null, '2023-06-06 16:24:27', 'admin', '2023-06-06 16:24:27', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089665194393600', 'SYS', '租户博客栏目', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogSectionController.list', null, 'GET', '{\"sectionName\":[\"\"],\"size\":[\"10\"],\"id\":[\"\"],\"page\":[\"1\"],\"parentId\":[\"\"],\"visitType\":[\"\"]}', '23', null, '2023-06-06 16:24:29', 'admin', '2023-06-06 16:24:29', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089668864409600', 'SYS', '租户博客标签', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTagController.list', null, 'GET', '{\"size\":[\"10\"],\"id\":[\"\"],\"page\":[\"1\"],\"tagName\":[\"\"],\"enableFlag\":[\"\"]}', '20', null, '2023-06-06 16:24:29', 'admin', '2023-06-06 16:24:29', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089672396013568', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '15', null, '2023-06-06 16:24:30', 'admin', '2023-06-06 16:24:30', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089673884991488', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '7', null, '2023-06-06 16:24:31', 'admin', '2023-06-06 16:24:31', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089677789888512', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '14', null, '2023-06-06 16:24:32', 'admin', '2023-06-06 16:24:32', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089680683958272', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '11', null, '2023-06-06 16:24:32', 'admin', '2023-06-06 16:24:32', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089713353392128', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '6', null, '2023-06-06 16:24:40', 'admin', '2023-06-06 16:24:40', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421089717614804992', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '8', null, '2023-06-06 16:24:41', 'admin', '2023-06-06 16:24:41', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421093815286235136', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '42', null, '2023-06-06 16:40:58', 'admin', '2023-06-06 16:40:58', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421094636879085568', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '28', null, '2023-06-06 16:44:14', 'admin', '2023-06-06 16:44:14', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421094639374696448', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '16', null, '2023-06-06 16:44:14', 'admin', '2023-06-06 16:44:14', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421094642121965568', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '17', null, '2023-06-06 16:44:15', 'admin', '2023-06-06 16:44:15', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421094644521107456', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '13', null, '2023-06-06 16:44:16', 'admin', '2023-06-06 16:44:16', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421094646609870848', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '9', null, '2023-06-06 16:44:16', 'admin', '2023-06-06 16:44:16', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421094648702828544', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '20', null, '2023-06-06 16:44:17', 'admin', '2023-06-06 16:44:17', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421094650577682432', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '14', null, '2023-06-06 16:44:17', 'admin', '2023-06-06 16:44:17', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421097400204001280', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '32', null, '2023-06-06 16:55:13', 'admin', '2023-06-06 16:55:13', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421097402225655808', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '12', null, '2023-06-06 16:55:13', 'admin', '2023-06-06 16:55:13', 'admin', '0');
INSERT INTO `sys_log` VALUES ('421097404402499584', 'SYS', '博客时间线', '分页查询', '2', '1', '1', '0:0:0:0:0:0:0:1', 'top.zsmile.system.boot.modules.blog.controller.BlogTimelineController.list', null, 'GET', '{\"size\":[\"10\"],\"year\":[\"\"],\"description\":[\"\"],\"id\":[\"\"],\"page\":[\"1\"],\"title\":[\"\"]}', '13', null, '2023-06-06 16:55:14', 'admin', '2023-06-06 16:55:14', 'admin', '0');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `parent_id` bigint(20) NOT NULL COMMENT '父ID,最上级则为0',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_icon` varchar(100) DEFAULT NULL COMMENT '菜单icon',
  `route_url` varchar(255) DEFAULT NULL COMMENT '路由地址',
  `route_view` varchar(255) DEFAULT NULL COMMENT '路由视图',
  `menu_type` tinyint(2) NOT NULL COMMENT '菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)',
  `perm` varchar(500) DEFAULT NULL COMMENT '权限标识',
  `order_num` smallint(5) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  `enable_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用，0禁用1启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('296769835235278850', '0', '系统管理', '', '/sys', '', '0', '', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913506779136', '296769835235278850', '菜单管理', null, '/sys/menu', '/sys/SysMenu', '1', null, '6', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913506779137', '298269913506779136', '查询', null, null, null, '2', 'sys:menu:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913506779138', '298269913506779136', '更新', null, null, null, '2', 'sys:menu:info;sys:menu:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913506779139', '298269913506779136', '新增', null, null, null, '2', 'sys:menu:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913506779140', '298269913506779136', '删除', null, null, null, '2', 'sys:menu:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913519362048', '296769835235278850', '数据字典信息', null, '/sys/dict/item', '/sys/SysDictItem', '1', null, '8', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913519362049', '298269913519362048', '查询', null, null, null, '2', 'sys:dict:item:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913519362050', '298269913519362048', '更新', null, null, null, '2', 'sys:dict:item:info;sys:dict:item:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913519362051', '298269913519362048', '新增', null, null, null, '2', 'sys:dict:item:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913519362052', '298269913519362048', '删除', null, null, null, '2', 'sys:dict:item:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913531944960', '296769835235278850', '系统配置', null, '/sys/config', '/sys/SysConfig', '1', null, '2', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913531944961', '298269913531944960', '查询', null, null, null, '2', 'sys:config:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913531944962', '298269913531944960', '更新', null, null, null, '2', 'sys:config:info;sys:config:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913531944963', '298269913531944960', '新增', null, null, null, '2', 'sys:config:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913531944964', '298269913531944960', '删除', null, null, null, '2', 'sys:config:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913540333568', '296769835235278850', '用户管理', null, '/sys/user', '/sys/SysUser', '1', null, '3', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913540333569', '298269913540333568', '查询', null, null, null, '2', 'sys:user:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913540333570', '298269913540333568', '更新', null, null, null, '2', 'sys:user:info;sys:user:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913540333571', '298269913540333568', '新增', null, null, null, '2', 'sys:user:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913540333572', '298269913540333568', '删除', null, null, null, '2', 'sys:user:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913552916480', '296769835235278850', '数据字典', null, '/sys/dict', '/sys/SysDict', '1', null, '7', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913552916481', '298269913552916480', '查询', null, null, null, '2', 'sys:dict:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913552916482', '298269913552916480', '更新', null, null, null, '2', 'sys:dict:info;sys:dict:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913552916483', '298269913552916480', '新增', null, null, null, '2', 'sys:dict:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913552916484', '298269913552916480', '删除', null, null, null, '2', 'sys:dict:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913578082304', '296769835235278850', '系统日志', null, '/sys/log', '/sys/SysLog', '1', null, '9', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913578082305', '298269913578082304', '查询', null, null, null, '2', 'sys:log:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913578082306', '298269913578082304', '更新', null, null, null, '2', 'sys:log:info;sys:log:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913578082307', '298269913578082304', '新增', null, null, null, '2', 'sys:log:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913578082308', '298269913578082304', '删除', null, null, null, '2', 'sys:log:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913590665216', '296769835235278850', '系统部门', null, '/sys/dept', '/sys/SysDept', '1', null, '4', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913590665217', '298269913590665216', '查询', null, null, null, '2', 'sys:dept:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913590665218', '298269913590665216', '更新', null, null, null, '2', 'sys:dept:info;sys:dept:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913590665219', '298269913590665216', '新增', null, null, null, '2', 'sys:dept:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913590665220', '298269913590665216', '删除', null, null, null, '2', 'sys:dept:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913599053824', '296769835235278850', '角色管理', null, '/sys/role', '/sys/SysRole', '1', null, '2', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913599053825', '298269913599053824', '查询', null, null, null, '2', 'sys:role:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913599053826', '298269913599053824', '更新', null, null, null, '2', 'sys:role:info;sys:role:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913599053827', '298269913599053824', '新增', null, null, null, '2', 'sys:role:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913599053828', '298269913599053824', '删除', null, null, null, '2', 'sys:role:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913599053829', '298269913599053824', '授权', null, null, null, '2', 'sys:role:menu:list;sys:role:menu:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913611636736', '296769835235278850', '多租户管理', null, '/sys/tenant', '/sys/SysTenant', '1', null, '1', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913611636737', '298269913611636736', '查询', null, null, null, '2', 'sys:tenant:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913611636738', '298269913611636736', '更新', null, null, null, '2', 'sys:tenant:info;sys:tenant:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913611636739', '298269913611636736', '新增', null, null, null, '2', 'sys:tenant:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298269913611636740', '298269913611636736', '删除', null, null, null, '2', 'sys:tenant:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286056837120', '298867137412333568', '博客评论', null, '/blog/comment', '/blog/BlogComment', '1', null, '4', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286056837121', '298866286056837120', '查询', null, null, null, '2', 'blog:comment:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286056837122', '298866286056837120', '更新', null, null, null, '2', 'blog:comment:info;blog:comment:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286056837123', '298866286056837120', '新增', null, null, null, '2', 'blog:comment:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286056837124', '298866286056837120', '删除', null, null, null, '2', 'blog:comment:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286077808640', '298867137412333568', '博客栏目', null, '/blog/section', '/blog/BlogSection', '1', null, '8', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286077808641', '298866286077808640', '查询', null, null, null, '2', 'blog:section:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286077808642', '298866286077808640', '更新', null, null, null, '2', 'blog:section:info;blog:section:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286077808643', '298866286077808640', '新增', null, null, null, '2', 'blog:section:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286077808644', '298866286077808640', '删除', null, null, null, '2', 'blog:section:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286098780160', '298867137412333568', '博客文章', null, '/blog/article', '/blog/BlogArticle', '1', null, '3', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286098780161', '298866286098780160', '查询', null, null, null, '2', 'blog:article:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286098780162', '298866286098780160', '更新', null, null, null, '2', 'blog:article:info;blog:article:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286098780163', '298866286098780160', '新增', null, null, null, '2', 'blog:article:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286098780164', '298866286098780160', '删除', null, null, null, '2', 'blog:article:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286115557376', '298867137412333568', '博客标签', null, '/blog/tag', '/blog/BlogTag', '1', null, '9', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286115557377', '298866286115557376', '查询', null, null, null, '2', 'blog:tag:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286115557378', '298866286115557376', '更新', null, null, null, '2', 'blog:tag:info;blog:tag:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286115557379', '298866286115557376', '新增', null, null, null, '2', 'blog:tag:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298866286115557380', '298866286115557376', '删除', null, null, null, '2', 'blog:tag:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('298867137412333568', '0', '博客管理', null, '/blog', null, '0', null, '0', '2022-07-04 09:55:47', 'admin', '2022-07-04 09:55:47', 'admin', '0', '1');
INSERT INTO `sys_menu` VALUES ('298878824412610560', '298867137412333568', '发表博客', null, '/blog/publish', '/blog/BlogPublish', '1', null, '0', '2022-07-04 10:42:14', 'admin', '2022-07-04 10:42:14', 'admin', '0', '1');
INSERT INTO `sys_menu` VALUES ('309048942035865600', '0', 'DEMO示例', null, '/demo/list', '/demo/DemoList', '1', null, '9', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('309048942035865601', '309048942035865600', '所有权限', null, null, null, '2', 'sys:demo:all', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('318178864713043968', '298867137412333568', '博客时间线', null, '/blog/timeline', '/blog/BlogTimeline', '1', null, '9', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('318178864713043969', '318178864713043968', '查询', null, null, null, '2', 'blog:timeline:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('318178864713043970', '318178864713043968', '更新', null, null, null, '2', 'blog:timeline:info;blog:timeline:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('318178864713043971', '318178864713043968', '新增', null, null, null, '2', 'blog:timeline:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('318178864713043972', '318178864713043968', '删除', null, null, null, '2', 'blog:timeline:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('344890180391538688', '298867137412333568', 'GIT文章同步', null, '/blog/git/article', '/blog/BlogGitArticle', '1', null, '4', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('344890180391538689', '344890180391538688', '查询', null, null, null, '2', 'blog:git:article:list', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('344890180391538690', '344890180391538688', '更新', null, null, null, '2', 'blog:git:article:info;blog:git:article:update', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('344890180391538691', '344890180391538688', '新增', null, null, null, '2', 'blog:git:article:save', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('344890180391538692', '344890180391538688', '删除', null, null, null, '2', 'blog:git:article:remove', '0', null, null, null, null, '0', '1');
INSERT INTO `sys_menu` VALUES ('344890180391538693', '344890180391538688', '发布', null, null, null, '2', 'blog:git:article:publish', '0', null, null, null, null, '0', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色管理';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('310185715658915840', '297516356822106112', '超级管理员', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `check_type` tinyint(2) NOT NULL COMMENT '选中状态1选中，2半选中',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统角色菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('310581560187289600', '310185715658915840', '298866286056837120', '1');
INSERT INTO `sys_role_menu` VALUES ('310581560187289601', '310185715658915840', '298866286056837121', '1');
INSERT INTO `sys_role_menu` VALUES ('310581560187289602', '310185715658915840', '298866286056837122', '1');
INSERT INTO `sys_role_menu` VALUES ('310581560187289603', '310185715658915840', '298866286056837123', '1');
INSERT INTO `sys_role_menu` VALUES ('310581560187289604', '310185715658915840', '298866286056837124', '1');
INSERT INTO `sys_role_menu` VALUES ('310581560187289605', '310185715658915840', '298867137412333568', '2');

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_name` varchar(100) NOT NULL COMMENT '租户名称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(50) NOT NULL COMMENT 'salt',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '是否启用，0禁用1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='多租户管理';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES ('297516356822106112', '笑笑庄', 'b6f855bd48d40f48694d082b45b8df7440c86956e43879acbf61f7c3fd8b6fbd', 'VgPzVrOUel3uh7K8uAgR', '1', '2022-06-30 16:28:16', 'admin', '2022-07-05 11:57:08', 'admin', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实名称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(50) NOT NULL COMMENT 'salt',
  `enable_flag` tinyint(1) DEFAULT '1' COMMENT '是否启用，0禁用1启用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '297516356822106112', 'admin', '超级管理员', 'CA0C68DB2823F561A559B210A0BCD4753E03D593B8C602898D4EE5DB65716A5F', 'u3DqjnGKtYK5w521D50O', '1', '超级管理员，密码123456', null, null, '2022-10-06 16:03:17', 'admin', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '角色id',
  `role_id` bigint(20) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户关联角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '310185715658915840');

-- ----------------------------
-- Table structure for temp
-- ----------------------------
DROP TABLE IF EXISTS `temp`;
CREATE TABLE `temp` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of temp
-- ----------------------------
