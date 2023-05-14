package top.zsmile.system.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
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
import top.zsmile.system.modules.sys.entity.SysDictEntity;
import top.zsmile.system.modules.sys.service.SysDictItemService;
import top.zsmile.system.modules.sys.entity.SysDictItemEntity;
import top.zsmile.system.modules.sys.service.SysDictService;

/**
 * 数据字典信息
 */
@Api(tags = "系统数据字典信息")
@RestController
@RequestMapping("/sys/dict/item")
public class SysDictItemController {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysDictItemService sysDictItemService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "数据字典信息", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("sys:dict:item:list")
    @GetMapping("/list")
    public R<IPage<SysDictItemEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = sysDictItemService.getPageByMap(params);
        return R.success("查询成功", page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("sys:dict:item:info")
    @GetMapping("/info/{id}")
    public R<SysDictItemEntity> info(@PathVariable("id") Long id) {
        SysDictItemEntity info = sysDictItemService.getById(id);
        return R.success("查询成功", info);
    }


    @ApiOperation("查询字典列表")
    @RequiresPermissions("sys:dict:item:info")
    @GetMapping("/info/dictList")
    public R<List<SysDictEntity>> dictList() {
        List<SysDictEntity> list = sysDictService.listByMap(null, "dictCode", "dictName");
        return R.success("查询成功", list);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "数据字典信息", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("sys:dict:item:update")
    @PostMapping("/update")
    public R update(@RequestBody SysDictItemEntity sysDictItemEntity) {
        SysDictEntity dictEntity = sysDictService.getById(sysDictItemEntity.getDictId());
        sysDictItemEntity.setDictCode(dictEntity.getDictCode());
        sysDictItemService.updateById(sysDictItemEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "数据字典信息", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("sys:dict:item:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        sysDictItemService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "数据字典信息", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("sys:dict:item:save")
    @PostMapping("/save")
    public R save(@RequestBody SysDictItemEntity sysDictItemEntity) {
        SysDictEntity dictEntity = sysDictService.getById(sysDictItemEntity.getDictId());
        sysDictItemEntity.setDictCode(dictEntity.getDictCode());
        sysDictItemService.save(sysDictItemEntity);
        return R.success("添加成功");
    }
}
