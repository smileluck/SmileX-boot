package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import top.zsmile.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.modules.sys.service.SysDictService;
import top.zsmile.modules.sys.entity.SysDictEntity;

/**
 * 系统数据字典
 */
@Api(tags = "系统数据字典")
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "数据字典", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:dict:list")
    @GetMapping("/list")
    public R<IPage<SysDictEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysDictService.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:dict:info")
    @GetMapping("/info/{id}")
    public R<SysDictEntity> info(@PathVariable("id") Long id){
        SysDictEntity info = sysDictService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "数据字典", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:dict:update")
    @PostMapping("/update")
    public R update(@RequestBody SysDictEntity sysDictEntity){
        sysDictService.updateById(sysDictEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "数据字典", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:dict:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysDictService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "数据字典", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:dict:save")
    @PostMapping("/save")
    public R save(@RequestBody SysDictEntity sysDictEntity){
        sysDictService.save(sysDictEntity);
        return R.success("添加成功");
    }
}
