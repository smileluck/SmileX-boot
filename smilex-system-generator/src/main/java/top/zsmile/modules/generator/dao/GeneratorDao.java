package top.zsmile.modules.generator.dao;

import java.util.List;
import java.util.Map;

public interface GeneratorDao {
    List<Map<String, Object>> selectTableList(String tableName);

    Map<String, String> selectTable(String tableName);

    List<Map<String, Object>> selectTableColumns(String tableName);
}
