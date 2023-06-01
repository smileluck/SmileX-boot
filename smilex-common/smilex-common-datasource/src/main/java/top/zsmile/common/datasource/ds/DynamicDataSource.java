package top.zsmile.common.datasource.ds;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.datasource.DataSourceContentHolder;
import top.zsmile.common.datasource.DataSourceFactory;
import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现多数数据源控制
 */
public class DynamicDataSource extends AbstractRoutingDataSource implements IDynamicDataSource {

    private static volatile DynamicDataSource INSTANCE;

    private static Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

    private static final ReentrantLock lock = new ReentrantLock();

    public static DynamicDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (DynamicDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DynamicDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public boolean containKey(Object key) {
        boolean b = dataSourceMap.containsKey(key);
        return b;
    }


    public void add(Object key, DataSourceProperties dataSourceProperties) {
        DataSource dataSource = DataSourceFactory.createDataSource(dataSourceProperties);
        add(key, dataSource);
    }

    public void add(Object key, DataSource dataSource) {
        boolean hasKey = dataSourceMap.containsKey(key);
        if (hasKey) {
//            if (o instanceof DataSource) {
//                DruidDataSource druidDataSource = (DruidDataSource) o;
//                druidDataSource.close();
//            }
            throw new SXException("数据库连接池 KEY 重复");
        }
        dataSourceMap.put(key, dataSource);
        setMap(dataSourceMap);
    }

    public void setMap(Map<Object, Object> objectObjectMap) {
        lock.lock();
        this.dataSourceMap = objectObjectMap;
        setPrimary();
        super.setTargetDataSources(dataSourceMap);
        super.afterPropertiesSet();
        lock.unlock();
    }

    /**
     * 设置主数据源
     */
    private void setPrimary() {
        Object o = this.dataSourceMap.get(DynamicDataSourceProperties.PRIMARY);
        if (o != null) {
            this.setDefaultTargetDataSource(o);
        }
    }

    public void del(Object key) {
        Object o = dataSourceMap.get(key);
        if (o != null) {
            if (o instanceof DataSource) {
                DruidDataSource dataSource = (DruidDataSource) o;
                if (dataSource != null) {
                    dataSource.close();
                    dataSourceMap.remove(key);
                    setMap(dataSourceMap);
                }
            } else {
                throw new SXException("数据池类型无法识别");
            }
        }
    }

    /**
     * @param key        被替换的数据源KEY
     * @param dataSource 数据源
     */
    public void replace(Object key, DataSource dataSource) {
        Object o = dataSourceMap.get(key);
        dataSourceMap.put(key, dataSource);
        if (DynamicDataSourceProperties.PRIMARY.equals(key)) {
            this.setDefaultTargetDataSource(dataSource);
        }
        if (o != null) {
            if (o instanceof DataSource) {
                DruidDataSource dataSource1 = (DruidDataSource) o;
                if (dataSource1 != null) {
                    dataSource1.close();
                }
            }
        }
        setMap(dataSourceMap);
    }

    /**
     * @param key                  被替换的数据源KEY
     * @param dataSourceProperties 数据源配置
     */
    public void replace(Object key, DataSourceProperties dataSourceProperties) {
        DruidDataSource dataSource = DataSourceFactory.createDataSource(dataSourceProperties);
        replace(key, dataSource);
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContentHolder.get();
    }

    @Override
    public Object get() {
        return get(getKey());
    }

    @Override
    public Object get(String key) {
        return dataSourceMap.get(key);
    }

    @Override
    public String getKey() {
        return DataSourceContentHolder.get();
    }


    /**
     * 处理多数据源的事务的关键
     * @return
     * @throws SQLException
     */
//    @Override
//    public Connection getConnection() throws SQLException {
//        DataSource dataSource =
//                super.getResolvedDataSources().get(getKey());
//        return dataSource.getConnection();
//    }
}
