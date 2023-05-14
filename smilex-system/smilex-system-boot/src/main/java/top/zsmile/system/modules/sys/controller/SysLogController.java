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
import top.zsmile.system.modules.sys.service.SysLogService;
import top.zsmile.system.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 */
@Api(tags = "系统日志")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:log:list")
    @GetMapping("/list")
    public R<IPage<SysLogEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysLogService.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:log:info")
    @GetMapping("/info/{id}")
    public R<SysLogEntity> info(@PathVariable("id") Long id){
        SysLogEntity info = sysLogService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:log:update")
    @PostMapping("/update")
    public R update(@RequestBody SysLogEntity sysLogEntity){
        sysLogService.updateById(sysLogEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:log:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysLogService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:log:save")
    @PostMapping("/save")
    public R save(@RequestBody SysLogEntity sysLogEntity){
        sysLogService.save(sysLogEntity);
        return R.success("添加成功");
    }
}
