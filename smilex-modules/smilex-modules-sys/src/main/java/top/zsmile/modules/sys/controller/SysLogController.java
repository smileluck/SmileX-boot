package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.common.core.api.R;
import top.zsmile.common.log.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.modules.sys.service.SysLogService;
import top.zsmile.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 */
@Tag(name  = "系统日志")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @Operation(summary="查询列表（分页）")
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:log:list")
    @GetMapping("/list")
    public R<IPage<SysLogEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysLogService.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @Operation(summary="根据Id查询信息")
    @RequiresPermissions("sys:log:info")
    @GetMapping("/info/{id}")
    public R<SysLogEntity> info(@PathVariable("id") Long id){
        SysLogEntity info = sysLogService.getById(id);
        return R.success("查询成功",info);
    }


    @Operation(summary="根据Id更新信息")
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:log:update")
    @PostMapping("/update")
    public R update(@RequestBody SysLogEntity sysLogEntity){
        sysLogService.updateById(sysLogEntity);
        return R.success("修改成功");
    }

    @Operation(summary="根据id列表批量删除")
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:log:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysLogService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @Operation(summary="保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "系统日志", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:log:save")
    @PostMapping("/save")
    public R save(@RequestBody SysLogEntity sysLogEntity){
        sysLogService.save(sysLogEntity);
        return R.success("添加成功");
    }
}
