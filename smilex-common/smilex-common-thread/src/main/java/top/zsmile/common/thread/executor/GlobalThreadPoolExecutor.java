package top.zsmile.common.thread.executor;

import com.alibaba.fastjson2.reader.ObjectReaderImplInstant;
import lombok.extern.slf4j.Slf4j;
import top.zsmile.common.thread.factory.BaseThreadFactory;
import top.zsmile.common.thread.utils.Threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class GlobalThreadPoolExecutor {

    private static ExecutorService INSTANCE;

    /**
     * 获取线程池
     */
    public static ExecutorService getInstance() {
        if (INSTANCE == null) {
            synchronized (INSTANCE) {
                if (INSTANCE == null) {
                    BaseThreadFactory.Builder builder = new BaseThreadFactory.Builder();
                    builder.prefix("Global-ThreadPool-");
                    builder.uncaughtExceptionHandler((t, err) -> {
                        log.error("threadName: {} , err => {}", t.getName(), err.getMessage());
                    });
                    INSTANCE = new ThreadPoolExecutor(50,
                            50,
                            0,
                            TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(), builder.build());
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 关闭线程池
     */
    public static void shutDownExecutorService() {
        Threads.shutdownAndAwaitTermination(INSTANCE);
    }

}
