package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysMenuService;
import top.zsmile.modules.sys.entity.SysMenuEntity;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = sysMenuService.getPage(params);
        return R.success(page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SysMenuEntity info = sysMenuService.getById(id);
        return R.success("查询成功", info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysMenuEntity sysMenuEntity) {
        sysMenuService.updateById(sysMenuEntity);
        return R.success();
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        sysMenuService.removeLogicBatchIds(Arrays.asList(ids));
        return R.success();
    }

    @PostMapping("/save")
    public R save(@RequestBody SysMenuEntity sysMenuEntity) {
        sysMenuService.save(sysMenuEntity);
        return R.success();
    }
}
