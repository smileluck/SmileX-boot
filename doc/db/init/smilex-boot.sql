/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 127.0.0.1:3306
 Source Schema         : smilex-boot

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 02/07/2022 18:31:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `app_upgrade`;
CREATE TABLE `app_upgrade`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `version_num` smallint(5) NOT NULL COMMENT 'APP版本号数字，例如101',
  `version_show` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'APP版本号展示，例如1.0.1',
  `version_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '版本说明',
  `app_size` double NOT NULL COMMENT '包大小',
  `upgrade_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否需要更新，0无需更新，1需要更新，2需要且强制更新',
  `min_version` smallint(5) NULL DEFAULT NULL COMMENT '最小APP版本号数字，低于该版本强制升级，例101',
  `download_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '下载地址',
  `audit_flag` tinyint(1) NOT NULL DEFAULT 1 COMMENT '应用商店是否审核通过，0未通过，1已通过',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'APP版本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_upgrade
-- ----------------------------

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `section_id` bigint(20) NOT NULL COMMENT '栏目ID',
  `tag_ids` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签id，以,分割',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `article_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章内容',
  `grammar_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '语法类型，1markdown，2html',
  `visit_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '访问类型,1通用类型，2统一密码，3独立密码',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '独立密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'salt',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户博客文章' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_article
-- ----------------------------

-- ----------------------------
-- Table structure for blog_section
-- ----------------------------
DROP TABLE IF EXISTS `blog_section`;
CREATE TABLE `blog_section`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `section_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '栏目名称',
  `visit_flag` tinyint(1) NULL DEFAULT NULL COMMENT '访问类型，0直接访问，1统一密码访问',
  `order_num` smallint(5) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户博客栏目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_section
-- ----------------------------

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '组合ID',
  `tag_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `enable_flag` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用，0禁用1启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户博客标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------

-- ----------------------------
-- Table structure for open_user
-- ----------------------------
DROP TABLE IF EXISTS `open_user`;
CREATE TABLE `open_user`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `mobile` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `source_tenant_id` bigint(20) NOT NULL COMMENT '来源租户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '开放用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置名称',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置key',
  `config_type` tinyint(2) NULL DEFAULT NULL COMMENT '配置类型，1text,2json',
  `config_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用，0启用1禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `parent_id` bigint(20) NOT NULL COMMENT '父级部门，不存在则为0',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `dict_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (297874820157145088, 'enableFlag', '启用状态', '0未启用，1启用', '2022-07-01 16:12:40', 'admin', '2022-07-01 16:12:40', 'admin', 0);
INSERT INTO `sys_dict` VALUES (297892445075537920, 'upgradeType', 'APP包更新类型', '是否需要更新，0无需更新，1需要更新，2需要且强制更新', '2022-07-01 17:22:42', 'admin', '2022-07-01 17:22:42', 'admin', 0);
INSERT INTO `sys_dict` VALUES (297893233105567744, 'sysMenuType', '系统菜单类型', '菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)', '2022-07-01 17:25:50', 'admin', '2022-07-02 09:28:15', 'admin', 0);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `dict_id` bigint(20) NULL DEFAULT NULL COMMENT '字典ID',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `dict_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据值',
  `dict_label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据显示项',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (297888703869616128, 297874820157145100, 'enableFlag', '0', '禁用', NULL, '2022-07-01 17:07:50', 'admin', '2022-07-01 17:07:50', 'admin', 0);
INSERT INTO `sys_dict_item` VALUES (297888758722723840, 297874820157145100, 'enableFlag', '1', '启用', NULL, '2022-07-01 17:08:03', 'admin', '2022-07-01 17:10:38', 'admin', 0);
INSERT INTO `sys_dict_item` VALUES (297892666132135936, 297892445075537900, 'upgradeType', '0', '不更新', NULL, '2022-07-01 17:23:35', 'admin', '2022-07-01 17:23:35', 'admin', 0);
INSERT INTO `sys_dict_item` VALUES (297892786697404416, 297892445075537920, 'upgradeType', '1', '需要更新', NULL, '2022-07-01 17:24:04', 'admin', '2022-07-01 17:24:04', 'admin', 0);
INSERT INTO `sys_dict_item` VALUES (297892850715066368, 297892445075537920, 'upgradeType', '2', '强制更新', NULL, '2022-07-01 17:24:19', 'admin', '2022-07-01 17:24:19', 'admin', 0);
INSERT INTO `sys_dict_item` VALUES (298208819480100864, 297893233105567744, 'sysMenuType', '0', '菜单组', NULL, '2022-07-02 14:19:52', 'admin', '2022-07-02 14:19:52', 'admin', 0);
INSERT INTO `sys_dict_item` VALUES (298208936132083712, 297893233105567744, 'sysMenuType', '1', '菜单', NULL, '2022-07-02 14:20:20', 'admin', '2022-07-02 14:20:20', 'admin', 0);
INSERT INTO `sys_dict_item` VALUES (298208989949198336, 297893233105567744, 'sysMenuType', '2', '按钮权限', NULL, '2022-07-02 14:20:33', 'admin', '2022-07-02 14:20:33', 'admin', 0);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `parent_id` bigint(20) NOT NULL COMMENT '父ID,最上级则为0',
  `menu_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单icon',
  `route_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `route_view` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由视图',
  `menu_type` tinyint(2) NOT NULL COMMENT '菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)',
  `perm` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `order_num` smallint(5) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  `enable_flag` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用，0禁用1启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (296769835235278850, 0, '系统管理', '', '/sys', '', 0, '', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (296770621763747840, 296769835235278850, '菜单管理', NULL, '/sys/menu', '/sys/SysMenu', 1, NULL, 2, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (296771582989172736, 296769835235278850, '用户管理', NULL, '/sys/user', '/sys/SysUser', 1, NULL, 1, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (296772417492090880, 296769835235278850, '配置管理', NULL, '/sys/config', '/sys/SysConfig', 1, NULL, 5, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (296774380115984384, 296769835235278850, '部门管理', NULL, '/sys/dept', '/sys/SysDept', 1, NULL, 4, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (296774562857615360, 296769835235278850, '日志管理', NULL, '/sys/log', '/sys/SysLog', 1, NULL, 99, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (296774723918888960, 296769835235278850, '多租户管理', NULL, '/sys/tenant', '/sys/SysTenant', 1, NULL, 0, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (296776220832432128, 296769835235278850, '数据字典', NULL, '/sys/dict', '/sys/SysDict', 1, NULL, 8, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (296777058028093440, 296769835235278850, '角色管理', NULL, '/sys/role', '/sys/SysRole', 1, NULL, 3, NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (297887974236880896, 296769835235278850, '数据字典明细', NULL, '/sys/dictitem', '/sys/SysDictItem', 1, NULL, 9, '2022-07-01 17:04:56', 'admin', '2022-07-01 17:04:56', 'admin', 1, 1);
INSERT INTO `sys_menu` VALUES (298269913506779136, 296769835235278850, '系统菜单管理', NULL, '/sys/menu', '/sys/SysMenu', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913506779137, 298269913506779136, '查询', NULL, NULL, NULL, 2, 'sys:menu:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913506779138, 298269913506779136, '更新', NULL, NULL, NULL, 2, 'sys:menu:info;sys:menu:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913506779139, 298269913506779136, '新增', NULL, NULL, NULL, 2, 'sys:menu:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913506779140, 298269913506779136, '删除', NULL, NULL, NULL, 2, 'sys:menu:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913519362048, 296769835235278850, '数据字典信息', NULL, '/sys/dict/item', '/sys/SysDictItem', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913519362049, 298269913519362048, '查询', NULL, NULL, NULL, 2, 'sys:dict:item:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913519362050, 298269913519362048, '更新', NULL, NULL, NULL, 2, 'sys:dict:item:info;sys:dict:item:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913519362051, 298269913519362048, '新增', NULL, NULL, NULL, 2, 'sys:dict:item:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913519362052, 298269913519362048, '删除', NULL, NULL, NULL, 2, 'sys:dict:item:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913531944960, 296769835235278850, '系统配置', NULL, '/sys/config', '/sys/SysConfig', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913531944961, 298269913531944960, '查询', NULL, NULL, NULL, 2, 'sys:config:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913531944962, 298269913531944960, '更新', NULL, NULL, NULL, 2, 'sys:config:info;sys:config:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913531944963, 298269913531944960, '新增', NULL, NULL, NULL, 2, 'sys:config:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913531944964, 298269913531944960, '删除', NULL, NULL, NULL, 2, 'sys:config:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913540333568, 296769835235278850, '系统用户管理', NULL, '/sys/user', '/sys/SysUser', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913540333569, 298269913540333568, '查询', NULL, NULL, NULL, 2, 'sys:user:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913540333570, 298269913540333568, '更新', NULL, NULL, NULL, 2, 'sys:user:info;sys:user:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913540333571, 298269913540333568, '新增', NULL, NULL, NULL, 2, 'sys:user:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913540333572, 298269913540333568, '删除', NULL, NULL, NULL, 2, 'sys:user:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913552916480, 296769835235278850, '数据字典', NULL, '/sys/dict', '/sys/SysDict', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913552916481, 298269913552916480, '查询', NULL, NULL, NULL, 2, 'sys:dict:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913552916482, 298269913552916480, '更新', NULL, NULL, NULL, 2, 'sys:dict:info;sys:dict:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913552916483, 298269913552916480, '新增', NULL, NULL, NULL, 2, 'sys:dict:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913552916484, 298269913552916480, '删除', NULL, NULL, NULL, 2, 'sys:dict:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913565499392, 296769835235278850, '系统角色菜单', NULL, '/sys/role/menu', '/sys/SysRoleMenu', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913565499393, 298269913565499392, '查询', NULL, NULL, NULL, 2, 'sys:role:menu:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913565499394, 298269913565499392, '更新', NULL, NULL, NULL, 2, 'sys:role:menu:info;sys:role:menu:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913565499395, 298269913565499392, '新增', NULL, NULL, NULL, 2, 'sys:role:menu:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913565499396, 298269913565499392, '删除', NULL, NULL, NULL, 2, 'sys:role:menu:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913578082304, 296769835235278850, '系统日志', NULL, '/sys/log', '/sys/SysLog', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913578082305, 298269913578082304, '查询', NULL, NULL, NULL, 2, 'sys:log:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913578082306, 298269913578082304, '更新', NULL, NULL, NULL, 2, 'sys:log:info;sys:log:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913578082307, 298269913578082304, '新增', NULL, NULL, NULL, 2, 'sys:log:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913578082308, 298269913578082304, '删除', NULL, NULL, NULL, 2, 'sys:log:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913590665216, 296769835235278850, '系统部门', NULL, '/sys/dept', '/sys/SysDept', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913590665217, 298269913590665216, '查询', NULL, NULL, NULL, 2, 'sys:dept:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913590665218, 298269913590665216, '更新', NULL, NULL, NULL, 2, 'sys:dept:info;sys:dept:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913590665219, 298269913590665216, '新增', NULL, NULL, NULL, 2, 'sys:dept:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913590665220, 298269913590665216, '删除', NULL, NULL, NULL, 2, 'sys:dept:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913599053824, 296769835235278850, '角色管理', NULL, '/sys/role', '/sys/SysRole', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913599053825, 298269913599053824, '查询', NULL, NULL, NULL, 2, 'sys:role:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913599053826, 298269913599053824, '更新', NULL, NULL, NULL, 2, 'sys:role:info;sys:role:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913599053827, 298269913599053824, '新增', NULL, NULL, NULL, 2, 'sys:role:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913599053828, 298269913599053824, '删除', NULL, NULL, NULL, 2, 'sys:role:remove', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913611636736, 296769835235278850, '多租户管理', NULL, '/sys/tenant', '/sys/SysTenant', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913611636737, 298269913611636736, '查询', NULL, NULL, NULL, 2, 'sys:tenant:list', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913611636738, 298269913611636736, '更新', NULL, NULL, NULL, 2, 'sys:tenant:info;sys:tenant:update', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913611636739, 298269913611636736, '新增', NULL, NULL, NULL, 2, 'sys:tenant:save', 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` VALUES (298269913611636740, 298269913611636736, '删除', NULL, NULL, NULL, 2, 'sys:tenant:remove', 0, NULL, NULL, NULL, NULL, 0, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户ID',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '多租户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (297516356822106112, '笑笑庄', '2022-06-30 16:28:16', 'admin', '2022-06-30 16:28:16', 'admin', 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'salt',
  `enable_flag` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用，0禁用1启用',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (292798030246051840, 297516356822106100, 'admin', '超级管理员', 'ca0c68db2823f561a559b210a0bcd4753e03d593b8c602898d4ee5db65716a5f', 'u3DqjnGKtYK5w521D50O', 1, '超级管理员', NULL, NULL, '2022-07-01 11:05:28', 'admin', 0);

-- ----------------------------
-- Table structure for temp
-- ----------------------------
DROP TABLE IF EXISTS `temp`;
CREATE TABLE `temp`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，1是，0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of temp
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
