package top.zsmile.test.basic.queue;

import com.sun.jmx.remote.internal.ArrayQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@Slf4j
@SpringBootTest
public class QueueTest {

    @Test
    public void queue() {
        // 先入先出
        Queue<String> queue = new LinkedList<>();

        // 加入到尾部加入
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        log.info("queue => {}", queue.toString());

        // 取出并删除元素，为空不抛出异常
        String poll = queue.poll();
        log.info("queue poll => {}", poll);
        log.info("queue => {}", queue.toString());

        // 取出并删除元素，为空抛出异常
        queue.remove();
        log.info("queue => ", queue.toString());

        // 取出首部元素，但不移除，队列为空不抛出异常
        String peek = queue.peek();
        log.info("queue peek => {}", peek);
        log.info("queue => {}", queue.toString());

        // 取出首部元素，但不移除，队列为空会抛出异常
        String element = queue.element();
        log.info("queue element => {}", element);
        log.info("queue => {}", queue.toString());

        // 如果不存在且未超过容器限制,插入一个元素到尾部
        boolean offer = queue.offer("5");
        log.info("queue offer => {}", offer);
        log.info("queue => {}", queue.toString());

        // 判断队列是否为空
        boolean empty = queue.isEmpty();
        log.info("queue isEmpty => {}", empty);

        // 清空队列
        queue.clear();
        log.info("queue => {}", queue.toString());

        // 判断队列是否为空
        empty = queue.isEmpty();
        log.info("queue isEmpty => {}", empty);
    }


    @Test
    public void deque() {
        Deque<String> deque = new ArrayDeque<>();

        log.info("deque add to Last");
        deque.add("1");
        deque.add("2");
        deque.add("3");
        deque.add("4");
        log.info("deque => {}", deque);

        log.info("deque push to First");
        deque.push("5");
        deque.push("6");
        deque.push("7");
        deque.push("8");
        log.info("deque => {}", deque);

        log.info("peek=>{}", deque.peek());

        // 首尾元素
//        deque.addFirst("first");
//        deque.addFirst("first2");
//        deque.addLast("last");
//        deque.addLast("last2");
//        log.info("deque => {}", deque);
//
//
//        // 删除元素
//        String poll = deque.poll();
//        String pollFirst = deque.pollFirst();
//        String pollLast = deque.pollLast();
//        log.info("deque poll=>{},poll first=>{},poll last=>{}", poll, pollFirst, pollLast);
//        log.info("deque => {}", deque);

    }
}
