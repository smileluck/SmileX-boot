package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysDictService;
import top.zsmile.modules.sys.entity.SysDictEntity;

/**
 * 数据字典
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @GetMapping("/list")
    public R list(Map<String, Object> params) {
        IPage page = sysDictService.getPage(params);
        return R.success("查询成功",page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysDictEntity info = sysDictService.getById(id);
        return R.success("查询成功",info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysDictEntity sysDictEntity){
        sysDictService.updateById(sysDictEntity);
        return R.success("修改成功");
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysDictService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @PostMapping("/save")
    public R save(@RequestBody SysDictEntity sysDictEntity){
        sysDictService.save(sysDictEntity);
        return R.success("添加成功");
    }
}
