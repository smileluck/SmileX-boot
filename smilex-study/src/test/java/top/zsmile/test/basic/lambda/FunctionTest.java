package top.zsmile.test.basic.lambda;

import org.junit.Test;

import java.util.function.Function;

public class FunctionTest {

    @Test
    public void test() {
        Integer integer = functionTest("123123", item -> {
            return Integer.valueOf(item);
        });
        System.out.println(integer);
    }

    public static <T, R> R functionTest(T obj, Function<T, R> function) {
        return function.apply(obj);
    }


    /**
     * 1. 执行apply(4)，将“4” 传入到fc2 转换为 Integer输出
     * 2. 在执行fc1 的apply方法，将Integer 4传入。
     */
    @Test
    public void test2() {
        Function<Integer, Integer> fc1 = i -> i * 2;
        Function<String, Integer> fc2 = i -> Integer.valueOf(i);
        System.out.println(fc1.compose(fc2).apply("4"));
    }


    /**
     * 1. 接收到 int 4 后，乘以2后，得出8返回
     * 2. 将 int 8 传入，转换为 String 返回
     */
    @Test
    public void test3() {
        Function<Integer, Integer> fc1 = i -> i * 2;
        Function<Integer, String> fc2 = i -> i + "";
        System.out.println(fc1.andThen(fc2).apply(4));
    }

}
