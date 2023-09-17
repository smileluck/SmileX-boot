package top.zsmile.test.interview;

import com.sun.org.apache.bcel.internal.generic.INEG;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class interview01 {

    /**
     * 测试 float 和 double 的 equal
     * <p>
     * equals方法都会比较类型
     */
    @Test
    public void test1() {
        Float f1 = new Float(0.1f);
        Float f2 = new Float(0.1f);
        Double d = new Double(0.1);
        log.info("f1==f2 ==> {}", f1 == f2);
        log.info("f1.equal(f2) ==> {}", f1.equals(f2));
        log.info("d.equal(f1) ==> {}", d.equals(f1));
        log.info("f2.equal(d) ==> {}", f2.equals(d));
    }

    /**
     * 测试 Long 和 Integer 的 equal
     * <p>
     * equals方法都会比较类型
     */
    @Test
    public void test2() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);
        Long l = new Long(1);
        log.info("i1==i2 ==> {}", i1 == i2);
        log.info("i1.equals(i2) ==> {}", i1.equals(i2));
        log.info("l.equals(i1) ==> {}", l.equals(i1));
        log.info("i2.equals(l) ==> {}", i2.equals(l));
    }

    /**
     * 测试出现pingpong 还是 pongping
     *
     * 发现都是pingpong。推测是线程开启是异步，需要经历ready->runnable，然后再获取线程时间片段执行，因此导致会慢。
     */
    @Test
    public void test3() {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    log.info("pong");
                }
            }.start();
            log.info("ping");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
