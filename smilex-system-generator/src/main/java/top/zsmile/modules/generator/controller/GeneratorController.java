package top.zsmile.modules.generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;
import top.zsmile.modules.generator.service.GeneratorSerivce;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorSerivce generatorService;

    @GetMapping("/list")
    public R list(String tableName) {
        List<Map<String, Object>> maps = generatorService.queryTableList(tableName);
        return R.success(maps);
    }

    @GetMapping("/info")
    public R info(String tableName) {
        Map<String, String> maps = generatorService.queryTable(tableName);
        return R.success(maps);
    }

    @GetMapping("/columns")
    public R columns(String tableName) {
        List<Map<String, Object>> maps = generatorService.queryTableColumns(tableName);
        return R.success(maps);
    }


}
