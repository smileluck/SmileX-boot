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
import top.zsmile.modules.sys.service.SysTenantService;
import top.zsmile.modules.sys.entity.SysTenantEntity;

/**
 * 多租户管理
 */
@Tag(name  = "系统多租户管理")
@RestController
@RequestMapping("/sys/tenant")
public class SysTenantController {

    @Autowired
    private SysTenantService sysTenantService;

    @Operation(summary="查询列表（分页）")
    @SysLog(title = "多租户管理", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:tenant:list")
    @GetMapping("/list")
    public R<IPage<SysTenantEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysTenantService.getPageByMap(params, "tenantName", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功",page);
    }

    @Operation(summary="根据Id查询信息")
    @RequiresPermissions("sys:tenant:info")
    @GetMapping("/info/{id}")
    public R<SysTenantEntity> info(@PathVariable("id") Long id){
        SysTenantEntity info = sysTenantService.getById(id, "tenantName");
        return R.success("查询成功",info);
    }


    @Operation(summary="根据Id更新信息")
    @SysLog(title = "多租户管理", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:tenant:update")
    @PostMapping("/update")
    public R update(@RequestBody SysTenantEntity sysTenantEntity){
        sysTenantService.updateById(sysTenantEntity);
        return R.success("修改成功");
    }

    @Operation(summary="根据id列表批量删除")
    @SysLog(title = "多租户管理", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:tenant:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysTenantService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @Operation(summary="保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "多租户管理", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:tenant:save")
    @PostMapping("/save")
    public R save(@RequestBody SysTenantEntity sysTenantEntity){
        sysTenantService.saveTenant(sysTenantEntity);
        return R.success("添加成功");
    }
}
