package top.zsmile.test.basic.lambda;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ConsumerTest {
    @Test
    public void test() {
        consumerAccept("qweqweqwe", (item) -> {
            String s = new StringBuilder(item.toString()).reverse().toString();
            System.out.println(s);
        });
    }

    private static <T> void consumerAccept(T name, Consumer<T> com) {
        com.accept(name);
    }

    @Test
    public void test2() {
        AtomicReference<String> str = new AtomicReference<>();
        str.set("1200");
//        String ff = "1";
        consumerAndThen(str, (item) -> {
            System.out.println(item);
            System.out.println("先执行了我");
//            ff = "2";
            String s = new StringBuilder(item.toString()).reverse().toString();
            System.out.println(s);
        }, item -> {
            System.out.println("后执行了我");
            System.out.println(item);
        });
    }

    private static <T> void consumerAndThen(T name, Consumer<T> com, Consumer<T> com2) {
        com.andThen(com2).accept(name);
    }
}
