package top.zsmile.test.demo;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2022/12/13/10:39
 * @ClassName: TestDemo
 * @Description: TestDemo
 */
public class TestDemo {
    @Test
    public void test() {
       /*
   需求：list列表中每个参数x2(暂时删除了x2的操作)，再返回回去，这里通过CompletableFuture批量进行操作
   这里通过 CompletableFuture 进行并发操作
*/

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<Integer> list2 = new CopyOnWriteArrayList();

        CompletableFuture[] completableFutures = list.stream().map(e -> CompletableFuture.runAsync(() -> {
//            System.out.println("3  - " + Thread.currentThread().getName());
            //这里睡眠1秒钟之后再add进去会为空，把这个add放睡眠上面或者删除就不会，原因不明
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            list2.add(e);
            System.out.println("睡醒了" + Thread.currentThread().getName() + "  -  " + e);
        })).toArray(CompletableFuture[]::new);

        System.out.println("获取结果，执行下面的操作会暂停一秒左右");
        CompletableFuture.allOf(completableFutures).join();

        System.out.println("打印结果（这里结果可能会为空）");
        list2.forEach(System.out::println);

    }

    @Test
    public void test2() {
        String s = Integer.toHexString(-85);
        String substring = s.substring(s.length()-4);
        System.out.println(substring);
    }


    @Test
    public void test3() {

        String s = Integer.toHexString(-85);
        String substring = s.substring(s.length()-4);
        System.out.println(substring);
    }
}
