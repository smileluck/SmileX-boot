package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysRoleMenuService;
import top.zsmile.modules.sys.entity.SysRoleMenuEntity;

/**
 * 系统角色菜单
 */
@RestController
@RequestMapping("/sys/role/menu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @RequiresPermissions("sys:role:menu:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = sysRoleMenuService.getPage(params);
        return R.success("查询成功",page);
    }

    @RequiresPermissions("sys:role:menu:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysRoleMenuEntity info = sysRoleMenuService.getById(id);
        return R.success("查询成功",info);
    }

    @RequiresPermissions("sys:role:menu:update")
    @PostMapping("/update")
    public R update(@RequestBody SysRoleMenuEntity sysRoleMenuEntity){
        sysRoleMenuService.updateById(sysRoleMenuEntity);
        return R.success("修改成功");
    }

    @RequiresPermissions("sys:role:menu:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysRoleMenuService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @RequiresPermissions("sys:role:menu:save")
    @PostMapping("/save")
    public R save(@RequestBody SysRoleMenuEntity sysRoleMenuEntity){
        sysRoleMenuService.save(sysRoleMenuEntity);
        return R.success("添加成功");
    }
}
