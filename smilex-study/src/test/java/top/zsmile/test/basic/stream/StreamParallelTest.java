package top.zsmile.test.basic.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/02/03/9:25
 * @ClassName: StreamParallelTest
 * @Description: StreamParallelTest
 */
@Slf4j
public class StreamParallelTest {


    @Test
    public void parallelTest() throws InterruptedException {
        IntStream range = IntStream.range(1, 10);
        range.parallel().forEach(item -> {
            getThreadName();
        });
        log.info("主线程参与协作了");

        log.info("sleep 5000 start");
        Thread.sleep(5000);
        log.info("sleep 5000 end");

        range = IntStream.range(1, 10);
        long count = range.mapToObj(e -> CompletableFuture.runAsync(() -> {
            getThreadName(e);
        })).map(CompletableFuture::join).count();
        log.info("主线程等待了,但是线程一直同一个");

        log.info("sleep 5000 start");
        Thread.sleep(5000);
        log.info("sleep 5000 end");

        range = IntStream.range(1, 10);
        CompletableFuture[] completableFutures = range.mapToObj(e -> CompletableFuture.runAsync(() -> {
            getThreadName(e);
        })).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        log.info("主线程等待了");

        log.info("sleep 5000 start");
        Thread.sleep(5000);
        log.info("sleep 5000 end");


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        range = IntStream.range(1, 10);
        completableFutures = range.mapToObj(e -> CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            getThreadName(e);
        }, executorService)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        log.info("主线程等待了");

        log.info("sleep 5000 start");
        Thread.sleep(5000);
        log.info("sleep 5000 end");


    }

    private void getThreadName() {
        getThreadName(-1);
    }

    private void getThreadName(Integer ier) {
        log.info("ThreadName:{}，value:{}", Thread.currentThread().getName(), ier);
    }
}
