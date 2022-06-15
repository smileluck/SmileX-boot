package top.zsmile.auth;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {

    @Test
    public void SecureRandomTest() {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.generateSeed(8);

    }

    private ThreadLocal<ThreadLocalRandom> threadLocalRandomThreadLocal = new ThreadLocal<ThreadLocalRandom>() {
        @Override
        protected ThreadLocalRandom initialValue() {
            return ThreadLocalRandom.current();
        }
    };


    @Test
    public void testThreadLocalRandom() {

        new Thread(() -> {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            while (true) {
                int i = current.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();
        new Thread(() -> {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            while (true) {
                int i = current.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();
        new Thread(() -> {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            while (true) {
                int i = current.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();
        new Thread(() -> {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            while (true) {
                int i = current.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();

        while (true) {
        }
    }

    @Test
    public void testRandom() {

        Random random = new Random();

        new Thread(() -> {
            while (true) {
                int i = random.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();
        new Thread(() -> {
            while (true) {
                int i = random.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                int i = random.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                int i = random.nextInt(1000);
                System.out.println(System.currentTimeMillis() + "--" + i);
            }
        }).start();


        while (true) {
        }
    }
}
