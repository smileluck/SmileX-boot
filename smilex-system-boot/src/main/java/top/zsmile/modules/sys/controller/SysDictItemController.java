package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysDictItemService;
import top.zsmile.modules.sys.entity.SysDictItemEntity;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/sys/dict/item")
public class SysDictItemController {

    @Autowired
    private SysDictItemService sysDictItemService;

    @RequiresPermissions("sys:dict:item:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = sysDictItemService.getPage(params);
        return R.success("查询成功",page);
    }

    @RequiresPermissions("sys:dict:item:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysDictItemEntity info = sysDictItemService.getById(id);
        return R.success("查询成功",info);
    }

    @RequiresPermissions("sys:dict:item:update")
    @PostMapping("/update")
    public R update(@RequestBody SysDictItemEntity sysDictItemEntity){
        sysDictItemService.updateById(sysDictItemEntity);
        return R.success("修改成功");
    }

    @RequiresPermissions("sys:dict:item:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysDictItemService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @RequiresPermissions("sys:dict:item:save")
    @PostMapping("/save")
    public R save(@RequestBody SysDictItemEntity sysDictItemEntity){
        sysDictItemService.save(sysDictItemEntity);
        return R.success("添加成功");
    }
}
