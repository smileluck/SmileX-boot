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
import top.zsmile.modules.sys.service.SysFileService;
import top.zsmile.modules.sys.entity.SysFileEntity;

/**
 * 
 */
@Api(tags = "")
@RestController
@RequestMapping("/sys/file")
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:file:list")
    @GetMapping("/list")
    public R<IPage<SysFileEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysFileService.getPage(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:file:info")
    @GetMapping("/info/{id}")
    public R<SysFileEntity> info(@PathVariable("id") Long id){
        SysFileEntity info = sysFileService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:file:update")
    @PostMapping("/update")
    public R update(@RequestBody SysFileEntity sysFileEntity){
        sysFileService.updateById(sysFileEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:file:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysFileService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:file:save")
    @PostMapping("/save")
    public R save(@RequestBody SysFileEntity sysFileEntity){
        sysFileService.save(sysFileEntity);
        return R.success("添加成功");
    }
}
