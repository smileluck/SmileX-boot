package top.zsmile.modules.generator.dao;

import top.zsmile.modules.generator.domain.model.ColumnModel;

import java.util.List;
import java.util.Map;

public interface GeneratorDao {
    List<Map<String, Object>> selectTableList(String tableName);

    Map<String, String> selectTable(String tableName);

    List<ColumnModel> selectTableColumns(String tableName);
}
