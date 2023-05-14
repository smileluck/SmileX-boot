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
import top.zsmile.system.modules.sys.service.SysDeptService;
import top.zsmile.system.modules.sys.entity.SysDeptEntity;

/**
 * 系统部门
 */
@Api(tags = "系统部门")
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "系统部门", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:dept:list")
    @GetMapping("/list")
    public R<IPage<SysDeptEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysDeptService.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:dept:info")
    @GetMapping("/info/{id}")
    public R<SysDeptEntity> info(@PathVariable("id") Long id){
        SysDeptEntity info = sysDeptService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "系统部门", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:dept:update")
    @PostMapping("/update")
    public R update(@RequestBody SysDeptEntity sysDeptEntity){
        sysDeptService.updateById(sysDeptEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "系统部门", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:dept:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysDeptService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "系统部门", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:dept:save")
    @PostMapping("/save")
    public R save(@RequestBody SysDeptEntity sysDeptEntity){
        sysDeptService.save(sysDeptEntity);
        return R.success("添加成功");
    }
}
