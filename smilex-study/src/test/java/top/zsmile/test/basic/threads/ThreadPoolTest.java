package top.zsmile.test.basic.threads;

import com.sun.jmx.remote.internal.ArrayQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

@Slf4j
@SpringBootTest
public class ThreadPoolTest {

    @Test
    public void thread() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        threadPoolExecutor.execute(() -> {
            try {
                log.info(Thread.currentThread().getName());
                int i = (1 / 0);
            } catch (Exception e) {
                log.error("发生异常 => {}", e);
            }
        });

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threadFactory() {


        // 统一处理异常
        ThreadFactory threadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setName("testThread");
            thread.setUncaughtExceptionHandler((t, err) -> {
                log.info(t.getName());
                log.error("err => {}", err);
            });
            return thread;
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), threadFactory);
//        threadPoolExecutor.setThreadFactory(threadFactory);
        threadPoolExecutor.execute(() -> {
            log.info(Thread.currentThread().getName());
            int i = (1 / 0);
        });

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
