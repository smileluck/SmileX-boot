package top.zsmile.modules.generator.service;


import java.util.List;
import java.util.Map;

public interface GeneratorSerivce {
    List<Map<String, Object>> queryTableList(String tableName);
    Map<String, String> queryTable(String tableName);
    List<Map<String, Object>> queryTableColumns(String tableName);
}
