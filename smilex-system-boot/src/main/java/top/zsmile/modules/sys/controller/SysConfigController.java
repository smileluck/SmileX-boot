package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysConfigService;
import top.zsmile.modules.sys.entity.SysConfigEntity;

/**
 * 系统配置
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @GetMapping("/list")
    public R list(Map<String, Object> params) {
        IPage page = sysConfigService.getPage(params);
        return R.success(page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysConfigEntity info = sysConfigService.getById(id);
        return R.success("查询成功",info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysConfigEntity sysConfigEntity){
        sysConfigService.updateById(sysConfigEntity);
        return R.success("修改成功");
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysConfigService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @PostMapping("/save")
    public R save(@RequestBody SysConfigEntity sysConfigEntity){
        sysConfigService.save(sysConfigEntity);
        return R.success("添加成功");
    }
}
