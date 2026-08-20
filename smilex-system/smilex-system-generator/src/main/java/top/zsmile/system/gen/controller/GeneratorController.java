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
import top.zsmile.tool.gen.service.GeneratorService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器接口
 * <p>
 * 可通过 smilex.generator.security.token 配置访问令牌（见 GeneratorSecurityFilter）。
 */
@Validated
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

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
        }
        return R.success(maps);
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

    /**
     * 预览生成代码（不落盘）
     *
     * @param templateType 模板类型：entity/mapper/service/serviceimpl/controller/xml/vue/vuemodel/sql
     */
    @PostMapping("/preview")
    public R preview(@Validated @RequestBody GeneratorEntity generatorEntity, @NotBlank String templateType) {
        String code = generatorService.previewCode(generatorEntity, templateType);
        return R.success("预览成功", code);
    }

    @PostMapping("/genFileByLocal")
    public R genFileByLocal(@Validated(Add.class) @RequestBody GeneratorEntity generatorEntity) {
        generatorService.genLocalCode(generatorEntity);
        return R.success();
    }

    @PostMapping("/genFileByZip")
    public void genFileByZip(@Validated @RequestBody GeneratorEntity generatorEntity, HttpServletResponse response) {
        File file = generatorService.genZipCode(generatorEntity);
        try {
            HttpFileUtils.downloadFile(file, response);
        } finally {
            if (!file.delete()) {
                file.deleteOnExit();
            }
        }
    }
}
