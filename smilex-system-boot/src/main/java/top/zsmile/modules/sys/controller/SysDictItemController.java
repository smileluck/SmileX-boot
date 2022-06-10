package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysDictItemService;
import top.zsmile.modules.sys.entity.SysDictItemEntity;

@RestController
@RequestMapping("/sys/dict/item")
public class SysDictItemController {

    @Autowired
    private SysDictItemService sysDictItemService;

    @GetMapping("/list")
    public R list(Map<String, Object> params) {
        IPage page = sysDictItemService.getPage(params);
        return R.success(page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysDictItemEntity info = sysDictItemService.getById(id);
        return R.success(info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysDictItemEntity sysDictItemEntity){
        sysDictItemService.updateById(sysDictItemEntity);
        return R.success();
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysDictItemService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success();
    }

    @PostMapping("/save")
    public R save(@RequestBody SysDictItemEntity sysDictItemEntity){
        sysDictItemService.save(sysDictItemEntity);
        return R.success();
    }
}
