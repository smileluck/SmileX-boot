package top.zsmile.modules.generator.service;



import top.zsmile.common.domain.ZipFileEntity;
import top.zsmile.modules.generator.domain.entity.GeneratorEntity;
import top.zsmile.modules.generator.domain.model.ColumnModel;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface GeneratorSerivce {
    List<Map<String, Object>> queryTableList(String tableName);

    Map<String, String> queryTable(String tableName);

    List<ColumnModel> queryTableColumns(String tableName);

    List<ZipFileEntity> genCodeZip(GeneratorEntity generatorEntity);

    File genCodeLocal(GeneratorEntity generatorEntity);
}

