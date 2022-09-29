package top.zsmile.test.basic.queue;

import com.sun.jmx.remote.internal.ArrayQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.Queue;


@Slf4j
@SpringBootTest
public class QueueTest {

    @Test
    public void queue() {
        // 先入先出
        Queue<String> strings = new LinkedList<>();

        // 从头部加入
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        System.out.println(strings);

        // 取出并删除元素，为空不抛出异常
        String poll = strings.poll();
        System.out.println(poll);
        System.out.println(strings);

        // 取出并删除元素，为空抛出异常
        strings.remove();
        System.out.println(strings);

        // 取出首部元素，但不移除，队列为空不抛出异常
        String peek = strings.peek();
        System.out.println(peek);
        System.out.println(strings);

        // 取出首部元素，但不移除，队列为空会抛出异常
        String element = strings.element();
        System.out.println(element);
        System.out.println(strings);

        // 如果不存在且未超过容器限制,插入一个元素到尾部
        boolean offer = strings.offer("5");
        System.out.println(offer);
        System.out.println(strings);

        // 判断队列是否为空
        boolean empty = strings.isEmpty();
        System.out.println(empty);

        // 清空队列
        strings.clear();
        System.out.println(strings);

        // 判断队列是否为空
        empty = strings.isEmpty();
        System.out.println(empty);
    }
}
