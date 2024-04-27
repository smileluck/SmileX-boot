package top.zsmile.system.gen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.core.utils.file.HttpFileUtils;
import top.zsmile.common.web.validator.group.Add;
import top.zsmile.common.core.api.R;
import top.zsmile.tool.gen.domain.entity.DatabaseConnEntity;
import top.zsmile.tool.gen.domain.entity.GeneratorEntity;
import top.zsmile.tool.gen.domain.model.ColumnModel;
import top.zsmile.tool.gen.service.GeneratorSerivce;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.List;
import java.util.Map;

@Validated
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
    public R info(@NotBlank String tableName) {
        Map<String, String> maps = generatorService.queryTable(tableName);
        if (maps == null) {
            return R.fail("查询不到该表结构");
        } else {
            return R.success(maps);
        }
    }

    @GetMapping("/columns")
    public R columns(@NotBlank String tableName) {
        List<ColumnModel> maps = generatorService.queryTableColumns(tableName);
        return R.success(maps);
    }

    @PostMapping("/connect")
    public R connect(@Validated @RequestBody DatabaseConnEntity databaseConnEntity) {
        generatorService.switchDs(databaseConnEntity);
        return R.success("连接成功");
    }

    @PostMapping("/genFileByLocal")
    public R genFileByLocal(@Validated(Add.class) @RequestBody GeneratorEntity generatorEntity, HttpServletResponse response) {
        generatorService.genLocalCode(generatorEntity);
        return R.success();
    }

    @PostMapping("/genFileByZip")
    public void genFileByZip(@Validated @RequestBody GeneratorEntity generatorEntity, HttpServletResponse response) {
        File file = generatorService.genZipCode(generatorEntity);
        HttpFileUtils.downloadFile(file, response);
        file.delete();
    }

    @PostMapping("/genSingleFile")
    public R genSingleFile(@Validated @RequestBody GeneratorEntity generatorEntity, HttpServletResponse response) {

//        response.setContentType("application/octet-stream");
//        response.setCharacterEncoding("utf-8");
//        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        return R.success();
    }
}
