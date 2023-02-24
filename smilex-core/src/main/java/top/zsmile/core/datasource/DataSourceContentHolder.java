package top.zsmile.core.datasource;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class DataSourceContentHolder {
    private static final ThreadLocal<Deque<String>> contentHolder = new ThreadLocal() {
        @Override
        protected Object initialValue() {
//            return new LinkedList<>();
            return new ArrayDeque<>();
        }
    };

    public static void add(String dataSource) {
        log.debug("添加数据源 => {}", dataSource);
        contentHolder.get().add(dataSource);
    }

    public static String get() {
        String ds = contentHolder.get().peek();
        log.debug("获取当前数据源 => {}", ds);
        return ds;
    }

    public static void poll() {
        Deque<String> queue = contentHolder.get();
        String ds = queue.poll();
        log.debug("移除数据源 => {}", ds);
        if (queue.isEmpty()) {
            contentHolder.remove();
        }
    }

    public static void call(String dataSource, Runnable callback) {
        try {
            add(dataSource);
            callback.run();
        } finally {
            poll();
        }
    }
}
