package top.zsmile.common.datasource.provider;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源获取
 */
public interface DynamicDataSourceProvider {

    /**
     * key是类型，DataSource是数据源
     *
     * @return
     */
    Map<String, DataSource> loadDataSource();
}
