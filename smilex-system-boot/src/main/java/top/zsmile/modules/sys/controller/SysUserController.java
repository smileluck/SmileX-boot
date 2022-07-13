package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import top.zsmile.annotation.SysLog;
import top.zsmile.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysUserService;
import top.zsmile.modules.sys.entity.SysUserEntity;

/**
 * 系统用户管理
 */
@Api(tags = "系统用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "系统用户管理", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:user:list")
    @GetMapping("/list")
    public R<IPage<SysUserEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysUserService.getPage(params, "id", "tenantId", "username", "realName", "enableFlag", "remark", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:user:info")
    @GetMapping("/info/{id}")
    public R<SysUserEntity> info(@PathVariable("id") Long id){
        SysUserEntity info = sysUserService.getById(id, "id", "tenantId", "username", "realName", "enableFlag", "remark", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "系统用户管理", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:user:update")
    @PostMapping("/update")
    public R update(@RequestBody SysUserEntity sysUserEntity){
        sysUserService.updateById(sysUserEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "系统用户管理", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:user:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysUserService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "系统用户管理", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:user:save")
    @PostMapping("/save")
    public R save(@RequestBody SysUserEntity sysUserEntity){
        sysUserService.saveUser(sysUserEntity);
        return R.success("添加成功");
    }
}
