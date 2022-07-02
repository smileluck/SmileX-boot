INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (, 0, '部门管理', NULL, '/sys/dept', '/sys/SysDept', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);


INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuId[0]}, ${menuModel.parentId}, '查询', NULL, NULL, NULL, 2, "${smallColonName}:list", 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuId[1]}, ${menuModel.parentId}, '更新', NULL, NULL, NULL, 2, "${smallColonName}:info;${smallColonName}:update", 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuId[2]}, ${menuModel.parentId}, '更新', NULL, NULL, NULL, 2, "${smallColonName}:save", 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuId[3]}, ${menuModel.parentId}, '删除', NULL, NULL, NULL, 2, "${smallColonName}:remove", 0, NULL, NULL, NULL, NULL, 0, 1);
