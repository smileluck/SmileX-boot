package top.zsmile.mybatis.datascope;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

@Slf4j
public class DataScopeContentHolder {
    private static final ThreadLocal<Deque<DataScopePerm>> contentHolder = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new ArrayDeque<>();
        }
    };

    public synchronized static void add(DataScopePerm dataSource) {
        log.debug("添加数据权限参数 => {}", dataSource);
        contentHolder.get().push(dataSource);
    }

    public static DataScopePerm get() {
        DataScopePerm ds = contentHolder.get().peek();
        log.debug("获取当前数据权限参数 => {}", ds);
        if (ds == null) {
            return DataScopeFactory.create();
        }
        return ds;
    }

    public static void poll() {
        Queue<DataScopePerm> queue = contentHolder.get();
        DataScopePerm ds = queue.poll();
        log.debug("移除数据权限参数 => {}", ds);
        if (queue.isEmpty()) {
            contentHolder.remove();
        }
    }

}
