package top.zsmile.test.basic.threads;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/02/07/17:50
 * @ClassName: CompletableFutureTest
 * @Description: CompletableFutureTest
 */
@Slf4j
public class CompletableFutureTest {


    private List<tempObj> testList = new ArrayList<>();

    @Before
    public void init() {
        for (int i = 0; i < 1000000; i++) {
            testList.add(new tempObj(i + "", RandomUtils.nextInt(100, 10000)));
        }
    }

    @Test
    public void create() throws ExecutionException, InterruptedException {
        // new CompletableFuture
        CompletableFuture<Object> completableFuture = new CompletableFuture<>();
        completableFuture.complete("create");
        Object join = completableFuture.join();
        log.info("result=>{}", join);

        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("completedFuture");
        String join1 = completedFuture.join();
        log.info("result=>{}", join1);

        completedFuture = CompletableFuture.completedFuture(null);
        log.info("result=>{}", completedFuture.join());
    }

    @Test
    public void create2() throws ExecutionException, InterruptedException {
        // new CompletableFuture
        CompletableFuture<Object> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            try {
                log.info("开始等待");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("结束等待");
            completableFuture.complete("create");
        }).start();
        Object join = completableFuture.join();
        log.info("result=>{}", join);
    }

    @Test
    public void runAsync() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            getThreadName(0);
        });
        log.info("result=>{}", future.join());
    }

    @Test
    public void supplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            getThreadName(0);
            return "success";
        });
        log.info("result=>{}", future.join());
    }

    @Test
    public void getResult() throws ExecutionException, InterruptedException {
        // get
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            getThreadName(0);
            return "success";
        });
        log.info("result=>{}", future.get());

        // get
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            getThreadName(0);
            try {
                int i = 1;
                while (i <= 5) {
                    Thread.sleep(1000);
                    log.debug("sleep1 1000");
                    i++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "success";
        });
        try {
            log.info("result1=>{}", future1.get(2000, TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            log.error("timeout1 => {}", e.getMessage());
        }

        // join
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            getThreadName(0);
            return "success";
        });

        log.info("result2=>{}", future2.join());

        // getNow
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            getThreadName(0);
            try {
                int i = 1;
                while (i <= 5) {
                    Thread.sleep(1000);
                    log.debug("sleep2 1000");
                    i++;
                }
                log.info("over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "success";
        });
        log.info("result3=>{}", future3.getNow("6666"));
        Thread.sleep(10000);
        log.info("result3=>{}", future3.getNow("6666"));
    }

    @Test
    public void getOrJoin() {
        try {
            CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> 1 / 0);
            log.info("join result => {}", integerCompletableFuture.join());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CompletableFuture<Integer> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> 1 / 0);
            log.info("get result => {}", integerCompletableFuture2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void flowRun(){
        CompletableFuture.supplyAsync(()->{
            return 1;
        });
    }


    @Test
    public void speedTest() {
        long l = System.currentTimeMillis();
        List<String> collect = testList.stream().map(item -> item.getAge() + RandomStringUtils.randomAlphabetic(10)).collect(Collectors.toList());
        long l2 = System.currentTimeMillis();
        log.info("sync done in {}", l2 - l);


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        l = System.currentTimeMillis();
        CompletableFuture[] completableFutures = testList.stream().map(item -> CompletableFuture.supplyAsync(() -> {
            return item.getAge() + RandomStringUtils.randomAlphabetic(10);
        }, executorService)).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFutures);

        l2 = System.currentTimeMillis();
        log.info("async done in {}", l2 - l);
    }


    private void getThreadName(Integer ier) {
        log.info("ThreadName:{}，value:{}", Thread.currentThread().getName(), ier);
    }
}


@Getter
class tempObj {
    private String name;
    private Integer age;

    public tempObj(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}