package top.zsmile.common.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;

import java.util.ArrayDeque;
import java.util.Deque;

@Slf4j
public final class DataSourceContentHolder {
    private static final ThreadLocal<Deque<String>> contentHolder = new NamedThreadLocal("dynamic-datasource") {
        @Override
        protected Object initialValue() {
//            return new LinkedList<>();
            return new ArrayDeque<>();
        }
    };

    private DataSourceContentHolder() {
    }

    /**
     * 给当前线程添加数据源
     *
     * @param dataSource
     */
    public static void add(String dataSource) {
        log.debug("添加数据源 => {}", dataSource);
        contentHolder.get().add(dataSource);
    }

    /**
     * 获取当前线程数据源
     *
     * @return
     */
    public static String get() {
        String ds = contentHolder.get().peek();
        ds = StringUtils.isNotBlank(ds) ? ds : DynamicDataSourceProperties.PRIMARY;
        log.debug("获取当前数据源 => {}", ds);
        return ds;
    }

    /**
     * 清空当前数据源
     * 如果当前数据源不为空，则会只移除队列的元素
     */
    public static void poll() {
        Deque<String> queue = contentHolder.get();
        String ds = queue.poll();
        log.debug("移除数据源 => {}", ds);
        if (queue.isEmpty()) {
            contentHolder.remove();
        }
    }


    /**
     * 嵌套执行方法
     *
     * @param dataSource
     * @param callback
     */
    public static void call(String dataSource, Runnable callback) {
        try {
            add(dataSource);
            callback.run();
        } finally {
            poll();
        }
    }
}
