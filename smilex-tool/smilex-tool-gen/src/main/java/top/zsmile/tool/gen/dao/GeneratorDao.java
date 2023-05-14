package top.zsmile.tool.gen.dao;

import top.zsmile.tool.gen.domain.model.ColumnModel;

import java.util.List;
import java.util.Map;

public interface GeneratorDao {
    List<Map<String, Object>> selectTableList(String tableName);

    Map<String, String> selectTable(String tableName);

    List<ColumnModel> selectTableColumns(String tableName);
}
