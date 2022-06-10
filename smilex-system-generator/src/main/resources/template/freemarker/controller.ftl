package ${packages}.${moduleName}.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import ${packages}.${moduleName}.service.${bigHumpClass}Service;
import ${packages}.${moduleName}.entity.${bigHumpClass}Entity;

@RestController
@RequestMapping("/${reqMapping}")
public class ${bigHumpClass}Controller {

    @Autowired
    private ${bigHumpClass}Service ${smallHumpClass}Service;

    @GetMapping("/list")
    public R list(Map<String, Object> params) {
        IPage page = ${smallHumpClass}Service.getPage(params);
        return R.success(page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ${bigHumpClass}Entity info = ${smallHumpClass}Service.getById(id);
        return R.success(info);
    }

    @PostMapping("/update")
    public R update(@RequestBody ${bigHumpClass}Entity ${smallHumpClass}Entity){
        ${smallHumpClass}Service.updateById(${smallHumpClass}Entity);
        return R.success();
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        ${smallHumpClass}Service.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success();
    }

    @PostMapping("/save")
    public R save(@RequestBody ${bigHumpClass}Entity ${smallHumpClass}Entity){
        ${smallHumpClass}Service.save(${smallHumpClass}Entity);
        return R.success();
    }
}
