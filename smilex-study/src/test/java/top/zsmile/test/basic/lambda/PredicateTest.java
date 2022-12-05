package top.zsmile.test.basic.lambda;

import org.junit.Test;

import java.util.function.Predicate;

public class PredicateTest {

    @Test
    public void test() {
        boolean checkBool = predicateTest("str", item -> {
            if (item instanceof String) {
                return true;
            }
            return false;
        });
        System.out.println(checkBool);
    }

    private static <T> boolean predicateTest(T obj, Predicate<T> predicate) {
        return predicate.test(obj);
    }

    @Test
    public void tes2() {
        boolean checkBool = predicateNegate("str", item -> {
            if (item instanceof String) {
                return true;
            }
            return false;
        });
        System.out.println(checkBool);
    }

    private static <T> boolean predicateNegate(T obj, Predicate<T> predicate) {
        // 等效于 !predicate.test(obj);
        return predicate.negate().test(obj);
    }


    /**
     * 1. 判断是否为String类型
     * 2. 判断是否以STR开始
     * 两个调价你必须同时满足
     */
    @Test
    public void test3() {
        boolean checkBool = predicateAnd(Integer.valueOf(123), item -> {
            System.out.println("type");
            if (String.class.isInstance(item)) {
                return true;
            }
            return false;
        }, item -> {
            System.out.println("starts");
            if (item.toString().toUpperCase().startsWith("STR")) {
                return true;
            }
            return false;
        });
        System.out.println(checkBool);
    }

    private static <T> boolean predicateAnd(T obj, Predicate<T> predicate, Predicate<T> predicate2) {
        // 等效于 predicate.test(obj) && predicate2.test(obj);
        return predicate.and(predicate2).test(obj);
    }


    /**
     * 1. 判断是否为Integer类型
     * 2. 判断是否以STR开始
     * 两个条件满足一个
     */
    @Test
    public void test4() {
        boolean checkBool = predicateOr(String.valueOf("STR111"), item -> {
            System.out.println("type");
            if (Integer.class.isInstance(item)) {
                return true;
            }
            return false;
        }, item -> {
            System.out.println("starts");
            if (item.toString().toUpperCase().startsWith("STR")) {
                return true;
            }
            return false;
        });
        System.out.println(checkBool);
    }

    private static <T> boolean predicateOr(T obj, Predicate<T> predicate, Predicate<T> predicate2) {
        // 等效于 predicate.test(obj) || predicate2.test(obj);
        return predicate.or(predicate2).test(obj);
    }


}
