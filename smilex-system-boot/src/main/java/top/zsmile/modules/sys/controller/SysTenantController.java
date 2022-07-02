package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysTenantService;
import top.zsmile.modules.sys.entity.SysTenantEntity;

/**
 * 多租户管理
 */
@RestController
@RequestMapping("/sys/tenant")
public class SysTenantController {

    @Autowired
    private SysTenantService sysTenantService;

    @RequiresPermissions("sys:tenant:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = sysTenantService.getPage(params);
        return R.success("查询成功",page);
    }

    @RequiresPermissions("sys:tenant:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysTenantEntity info = sysTenantService.getById(id);
        return R.success("查询成功",info);
    }

    @RequiresPermissions("sys:tenant:update")
    @PostMapping("/update")
    public R update(@RequestBody SysTenantEntity sysTenantEntity){
        sysTenantService.updateById(sysTenantEntity);
        return R.success("修改成功");
    }

    @RequiresPermissions("sys:tenant:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysTenantService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @RequiresPermissions("sys:tenant:save")
    @PostMapping("/save")
    public R save(@RequestBody SysTenantEntity sysTenantEntity){
        sysTenantService.save(sysTenantEntity);
        return R.success("添加成功");
    }
}
