INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.parentId?c}, 0, '${tableComment}', NULL, '/${reqMapping}', '/${moduleName}/${bigHumpClass}', 1, NULL, 4, NULL, NULL, NULL, NULL, 0, 1);


INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuIds[0]?c}, ${menuModel.parentId?c}, '查询', NULL, NULL, NULL, 2, "${smallColonName}:list", 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuIds[1]?c}, ${menuModel.parentId?c}, '更新', NULL, NULL, NULL, 2, "${smallColonName}:info;${smallColonName}:update", 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuIds[2]?c}, ${menuModel.parentId?c}, '新增', NULL, NULL, NULL, 2, "${smallColonName}:save", 0, NULL, NULL, NULL, NULL, 0, 1);
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_icon`, `route_url`, `route_view`, `menu_type`, `perm`, `order_num`, `create_time`, `create_by`, `update_time`, `update_by`, `del_flag`, `enable_flag`)
VALUES (${menuModel.menuIds[3]?c}, ${menuModel.parentId?c}, '删除', NULL, NULL, NULL, 2, "${smallColonName}:remove", 0, NULL, NULL, NULL, NULL, 0, 1);
