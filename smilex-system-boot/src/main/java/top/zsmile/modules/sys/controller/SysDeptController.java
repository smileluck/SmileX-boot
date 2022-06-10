package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysDeptService;
import top.zsmile.modules.sys.entity.SysDeptEntity;

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping("/list")
    public R list(Map<String, Object> params) {
        IPage page = sysDeptService.getPage(params);
        return R.success(page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysDeptEntity info = sysDeptService.getById(id);
        return R.success(info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysDeptEntity sysDeptEntity){
        sysDeptService.updateById(sysDeptEntity);
        return R.success();
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        sysDeptService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success();
    }

    @PostMapping("/save")
    public R save(@RequestBody SysDeptEntity sysDeptEntity){
        sysDeptService.save(sysDeptEntity);
        return R.success();
    }
}
