package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import top.zsmile.annotation.SysLog;
import top.zsmile.common.constant.CommonConstant;
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

    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:config:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = sysConfigService.getPage(params);
        return R.success("查询成功",page);
    }

    @RequiresPermissions("sys:config:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysConfigEntity info = sysConfigService.getById(id);
        return R.success("查询成功",info);
    }


    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:config:update")
    @PostMapping("/update")
    public R update(@RequestBody SysConfigEntity sysConfigEntity){
        sysConfigService.updateById(sysConfigEntity);
        return R.success("修改成功");
    }

    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:config:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysConfigService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:config:save")
    @PostMapping("/save")
    public R save(@RequestBody SysConfigEntity sysConfigEntity){
        sysConfigService.save(sysConfigEntity);
        return R.success("添加成功");
    }
}
