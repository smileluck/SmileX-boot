package top.zsmile.system.modules.generator.service;


import top.zsmile.common.core.domain.ZipFileEntity;
import top.zsmile.system.modules.generator.domain.entity.GeneratorEntity;
import top.zsmile.system.modules.generator.domain.model.ColumnModel;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface GeneratorSerivce {
    List<Map<String, Object>> queryTableList(String tableName);

    Map<String, String> queryTable(String tableName);

    List<ColumnModel> queryTableColumns(String tableName);

    File genZipCode(GeneratorEntity generatorEntity);

    void genLocalCode(GeneratorEntity generatorEntity);
}

