package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysLogService;
import top.zsmile.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/list")
    public R list(Map<String, Object> params) {
        IPage page = sysLogService.getPage(params);
        return R.success(page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysLogEntity info = sysLogService.getById(id);
        return R.success("查询成功",info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysLogEntity sysLogEntity){
        sysLogService.updateById(sysLogEntity);
        return R.success("修改成功");
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysLogService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @PostMapping("/save")
    public R save(@RequestBody SysLogEntity sysLogEntity){
        sysLogService.save(sysLogEntity);
        return R.success("添加成功");
    }
}
