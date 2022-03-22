package top.zsmile.modules.generator.service;


import top.zsmile.common.entity.ZipFile;
import top.zsmile.common.utils.ZipUtils;
import top.zsmile.modules.generator.entity.GeneratorEntity;

import java.util.List;
import java.util.Map;

public interface GeneratorSerivce {
    List<Map<String, Object>> queryTableList(String tableName);

    Map<String, String> queryTable(String tableName);

    List<Map<String, Object>> queryTableColumns(String tableName);

    List<ZipFile> genCodeZip(GeneratorEntity generatorEntity);
}

