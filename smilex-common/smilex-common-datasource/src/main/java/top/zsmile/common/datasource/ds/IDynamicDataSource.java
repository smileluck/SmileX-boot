package top.zsmile.common.datasource.ds;

import com.alibaba.druid.pool.DruidDataSource;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.datasource.DataSourceContentHolder;
import top.zsmile.common.datasource.DataSourceFactory;
import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * 动态数据源配置
 */
interface IDynamicDataSource {

    /**
     * 是否包含数据源
     *
     * @param key 数据源KEY
     * @return
     */
    boolean containKey(Object key);

    /**
     * 添加数据源
     *
     * @param key                  数据源KEY
     * @param dataSourceProperties 数据源配置
     */
    void add(Object key, DataSourceProperties dataSourceProperties);

    /**
     * 添加数据源
     *
     * @param key        数据源KEY
     * @param dataSource 数据源
     */
    void add(Object key, DataSource dataSource);

    /**
     * 覆盖数据源Maps
     *
     * @param objectObjectMap
     */
    void setMap(Map<Object, Object> objectObjectMap);

    /**
     * 删除数据源
     *
     * @param key
     */
    void del(Object key);

    /**
     * 替换数据源
     * <p>
     * 如果数据源不存在则添加，存在则替换
     * </p>
     *
     * @param key        被替换的数据源KEY
     * @param dataSource 数据源
     */
    void replace(Object key, DataSource dataSource);

    /**
     * 替换数据源
     * <p>
     * 如果数据源不存在则添加，存在则替换
     * </p>
     *
     * @param key                  被替换的数据源KEY
     * @param dataSourceProperties 数据源配置
     */
    void replace(Object key, DataSourceProperties dataSourceProperties);

    /**
     * 获取当前数据源
     *
     * @return 数据源
     */
    Object get();

    /**
     * 通过数据源KEY，获取数据源
     *
     * @param key 数据源，不存在则为NULL
     * @return
     */
    Object get(String key);

    /**
     * 获取当前数据源的Key
     *
     * @return 数据源Key
     */
    String getKey();

}
