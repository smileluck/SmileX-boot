package top.zsmile.modules.sys.controller;

import java.util.*;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import top.zsmile.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.modules.sys.entity.SysMenuEntity;
import top.zsmile.modules.sys.entity.dto.SysRoleMenuSaveDto;
import top.zsmile.modules.sys.entity.vo.SysRoleMenuVo;
import top.zsmile.modules.sys.service.SysMenuService;
import top.zsmile.modules.sys.service.SysRoleMenuService;
import top.zsmile.modules.sys.service.SysRoleService;
import top.zsmile.modules.sys.entity.SysRoleEntity;

import javax.validation.Valid;

/**
 * 角色管理
 */
@Api(tags = "系统角色管理")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "角色管理", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:role:list")
    @GetMapping("/list")
    public R<IPage<SysRoleEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysRoleService.getPageByMap(params);
        return R.success("查询成功", page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:role:info")
    @GetMapping("/info/{id}")
    public R<SysRoleEntity> info(@PathVariable("id") Long id) {
        SysRoleEntity info = sysRoleService.getById(id);
        return R.success("查询成功", info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "角色管理", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:role:update")
    @PostMapping("/update")
    public R update(@RequestBody SysRoleEntity sysRoleEntity) {
        sysRoleService.updateById(sysRoleEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "角色管理", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:role:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        sysRoleService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "角色管理", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:role:save")
    @PostMapping("/save")
    public R save(@RequestBody SysRoleEntity sysRoleEntity) {
        sysRoleService.save(sysRoleEntity);
        return R.success("添加成功");
    }


    @ApiOperation("查询角色菜单")
    @SysLog(title = "角色管理", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "获取角色授权信息")
    @RequiresPermissions("sys:role:menu:list")
    @GetMapping("/menu/{id}")
    public R<SysRoleMenuVo> roleMenu(@PathVariable("id") Long id) {
        List<SysMenuEntity> menus = sysMenuService.list("parentId", "menuName", "menuType", "routeUrl", "perm");
        Map<String, Object> map = new HashMap<>(2);
        map.put("roleId", id);
        map.put("checkType", 1);
        List<Object> roleMenu = sysRoleMenuService.getSingleByMap(map, "menuId");
        return R.success(new SysRoleMenuVo(menus, roleMenu));
    }

    @ApiOperation("保存角色菜单")
    @SysLog(title = "角色管理", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "保存角色授权信息")
    @RequiresPermissions("sys:role:menu:save")
    @PostMapping("/menu/save")
    public R save(@Valid @RequestBody SysRoleMenuSaveDto sysRoleMenuSaveDto) {
        sysRoleMenuService.saveRoleMenus(sysRoleMenuSaveDto);
        return R.success("添加成功");
    }
}
