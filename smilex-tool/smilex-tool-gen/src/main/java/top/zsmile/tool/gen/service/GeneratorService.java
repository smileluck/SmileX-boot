package top.zsmile.tool.gen.service;

import top.zsmile.tool.gen.domain.entity.DatabaseConnEntity;
import top.zsmile.tool.gen.domain.entity.GeneratorEntity;
import top.zsmile.tool.gen.domain.model.ColumnModel;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface GeneratorService {

    /**
     * 查询数据库表列表（仅 BASE TABLE）
     */
    List<Map<String, Object>> queryTableList(String tableName);

    /**
     * 查询表结构信息
     */
    Map<String, String> queryTable(String tableName);

    /**
     * 查询表字段
     */
    List<ColumnModel> queryTableColumns(String tableName);

    /**
     * 生成ZIP压缩包（暂存区在系统临时目录，用毕清理）
     */
    File genZipCode(GeneratorEntity generatorEntity);

    /**
     * 根据本地路径生成
     */
    void genLocalCode(GeneratorEntity generatorEntity);

    /**
     * 预览单个模板的生成结果（不落盘）
     *
     * @param generatorEntity 生成参数（取 tableName 列表第一张表）
     * @param templateType    模板类型（DefaultConstants.TEMPLATE_TYPES 之一）
     * @return 渲染后的代码文本
     */
    String previewCode(GeneratorEntity generatorEntity, String templateType);

    /**
     * 切换连接的数据源（带参数校验与连接试探）
     */
    void switchDs(DatabaseConnEntity databaseConnEntity);
}
