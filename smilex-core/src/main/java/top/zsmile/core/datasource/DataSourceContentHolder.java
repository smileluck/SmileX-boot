package top.zsmile.core.datasource;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class DataSourceContentHolder {
    private static final ThreadLocal<Queue<String>> contentHolder = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new LinkedList<>();
        }
    };

    public synchronized static void setDataSource(String dataSource) {
        log.debug("添加数据源 => {}", dataSource);
        contentHolder.get().add(dataSource);
    }

    public static String getDataSource() {
        String ds = contentHolder.get().peek();
        log.debug("获取当前数据源 => {}", ds);
        return ds;
    }

    public static void pollDataSource() {
        Queue<String> queue = contentHolder.get();
        String ds = queue.poll();
        log.debug("移除数据源 => {}", ds);
        if (queue.isEmpty()) {
            contentHolder.remove();
        }
    }
}
