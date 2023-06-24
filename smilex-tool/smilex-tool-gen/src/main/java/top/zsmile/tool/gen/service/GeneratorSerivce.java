package top.zsmile.tool.gen.service;


import top.zsmile.tool.gen.domain.entity.DatabaseConnEntity;
import top.zsmile.tool.gen.domain.entity.GeneratorEntity;
import top.zsmile.tool.gen.domain.model.ColumnModel;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface GeneratorSerivce {
    /**
     * 查询数据库表列表
     *
     * @param tableName
     * @return
     */
    List<Map<String, Object>> queryTableList(String tableName);

    /**
     * 查询表结构信息
     *
     * @param tableName
     * @return
     */
    Map<String, String> queryTable(String tableName);

    /**
     * 查询表字段
     *
     * @param tableName
     * @return
     */
    List<ColumnModel> queryTableColumns(String tableName);

    /**
     * 生成ZIP压缩包
     *
     * @param generatorEntity
     * @return
     */
    File genZipCode(GeneratorEntity generatorEntity);

    /**
     * 根据本地路径创建压缩包
     *
     * @param generatorEntity
     */
    void genLocalCode(GeneratorEntity generatorEntity);

    /**
     * 切换连接的数据源
     *
     * @param databaseConnEntity
     */
    void switchDs(DatabaseConnEntity databaseConnEntity);

}

