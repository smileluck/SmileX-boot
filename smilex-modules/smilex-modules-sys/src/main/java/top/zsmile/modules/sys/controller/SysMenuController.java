package top.zsmile.modules.sys.controller;

import java.util.*;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.common.core.api.R;
import top.zsmile.common.log.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.modules.sys.service.SysMenuService;
import top.zsmile.modules.sys.entity.SysMenuEntity;

/**
 * 系统菜单管理
 */
@Api(tags = "系统菜单管理")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "系统菜单管理", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:menu:list")
    @GetMapping("/list")
    public R<IPage<SysMenuEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysMenuService.getPageByMap(params, true);
        return R.success("查询成功", page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:menu:info")
    @GetMapping("/info/{id}")
    public R<SysMenuEntity> info(@PathVariable("id") Long id) {
        SysMenuEntity info = sysMenuService.getById(id);
        return R.success("查询成功", info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "系统菜单管理", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:menu:update")
    @PostMapping("/update")
    public R update(@RequestBody SysMenuEntity sysMenuEntity) {
        sysMenuService.updateById(sysMenuEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "系统菜单管理", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:menu:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        sysMenuService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "系统菜单管理", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:menu:save")
    @PostMapping("/save")
    public R save(@RequestBody SysMenuEntity sysMenuEntity) {
        sysMenuService.save(sysMenuEntity);
        return R.success("添加成功");
    }

    /**
     * vue3 前端获取菜单和权限
     *
     * @return
     */
    @GetMapping("/perms")
    public R perms() {
        List<Map<String, Object>> menus = sysMenuService.queryMenusByUser();
        Set<String> perms = sysMenuService.queryPermsByUser();
        Map<String, Object> result = new HashMap<>(2);
        result.put("menus", menus);
        result.put("perms", perms);

        return R.success(result);
    }
}
