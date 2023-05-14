package top.zsmile.system.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import top.zsmile.common.log.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.system.modules.sys.service.SysConfigService;
import top.zsmile.system.modules.sys.entity.SysConfigEntity;

/**
 * 系统配置
 */
@Api(tags = "系统配置")
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:config:list")
    @GetMapping("/list")
    public R<IPage<SysConfigEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysConfigService.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:config:info")
    @GetMapping("/info/{id}")
    public R<SysConfigEntity> info(@PathVariable("id") Long id){
        SysConfigEntity info = sysConfigService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:config:update")
    @PostMapping("/update")
    public R update(@RequestBody SysConfigEntity sysConfigEntity){
        sysConfigService.updateById(sysConfigEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:config:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysConfigService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "系统配置", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:config:save")
    @PostMapping("/save")
    public R save(@RequestBody SysConfigEntity sysConfigEntity){
        sysConfigService.save(sysConfigEntity);
        return R.success("添加成功");
    }
}
