package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysMenuService;
import top.zsmile.modules.sys.entity.SysMenuEntity;

/**
 * 系统菜单管理
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = sysMenuService.getPage(params);
        return R.success("查询成功", page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SysMenuEntity info = sysMenuService.getById(id);
        return R.success("查询成功", info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysMenuEntity sysMenuEntity) {
        sysMenuService.updateById(sysMenuEntity);
        return R.success("修改成功");
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        sysMenuService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @PostMapping("/save")
    public R save(@RequestBody SysMenuEntity sysMenuEntity) {
        sysMenuService.save(sysMenuEntity);
        return R.success("添加成功");
    }

    /**
     * vue3 前端获取菜单和权限
     * TODO 查询实际授权的
     * @return
     */
    @GetMapping("/perms")
    public R perms() {
        List<Map<String, Object>> menus = sysMenuService.queryMenusByUser();
        List<Object> perms = sysMenuService.queryPermsByUser();

        Map<String, List> result = new HashMap<>(2);
        result.put("menus", menus);
        result.put("perms", perms);

        return R.success(result);
    }
}
