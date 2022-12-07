package top.zsmile.test.basic.stream;

import junit.extensions.TestSetup;
import lombok.extern.slf4j.Slf4j;
import okio.Options;
import org.junit.Before;
import org.junit.Test;
import top.zsmile.test.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class StreamTest {

    private static List<User> userList;

    @Before
    public void init() {
        userList = new ArrayList<>();
        userList.add(User.of("张三", 18, Arrays.asList("ADMIN", "USER")));
        userList.add(User.of("李四", 19, Arrays.asList("TEMP:USER")));
        userList.add(User.of("王五", 20, Arrays.asList("USER")));
    }

    @Test
    public void streamInit() {
        List<String> array = Arrays.asList("a", "b", "c");
        Stream<String> stream = array.stream();

        int[] arr = {1, 2, 3, 4};
        IntStream stream1 = Arrays.stream(arr);

        Stream<String> q = Stream.of("q", "w", "e");

        Stream<Integer> iterate = Stream.iterate(0, integer -> integer + 1);

        Stream<Double> limit = Stream.generate(Math::random).limit(10);
    }

    @Test
    public void foreachAndPeek() {
        log.info("start foreach");
        userList.stream().forEach(System.out::println);
        log.info("end foreach");

        log.info("start peek");
        userList.stream().peek(System.out::println);
        log.info("end peek");

        log.info("start peek -> count");
        userList.stream().peek(System.out::println).count();
        log.info("end peek -> count");
    }

    /**
     * 过滤年龄小于19岁的用户
     */
    @Test
    public void filter() {
        log.info("原数据：");
        userList.stream().forEach(System.out::println);
        log.info("过滤后数据：");
        userList.stream().filter(item -> item.getAge() >= 19).forEach(System.out::println);
    }

    @Test
    public void distinct() {
        List<String> strings = Arrays.asList("A", "B", "C", "D", "A", "B");
        log.info("原数据");
        strings.stream().forEach(System.out::println);
        log.info("去重后数据");
        strings.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void sorted() {
        List<Integer> list = Arrays.asList(1, 5, 6, 3, 4, 8, 48);
        log.info("基本类型Integer默认排序后数据：");
        list.stream().sorted().forEach(System.out::println);

        List<String> list2 = Arrays.asList("a", "cc", "b", "e", "s", "m");
        log.info("基本类型String默认排序后数据：");
        list2.stream().sorted().forEach(System.out::println);

        log.info("自定义排序后数据：");
        userList.stream().sorted((o1, o2) -> {
            log.info("排序比较值:{}", o2.getAge() + o1.getAge());
            return o2.getAge() + o1.getAge();
        }).forEach(System.out::println);
    }

    @Test
    public void limitAndSkip() {
        log.info("原数据");
        userList.stream().forEach(System.out::println);
        log.info("取前两个");
        userList.stream().limit(2).forEach(System.out::println);
        log.info("跳过第一个");
        userList.stream().skip(1).forEach(System.out::println);
        log.info("获取中间一个");
        userList.stream().skip(1).limit(1).forEach(System.out::println);
    }

    @Test
    public void map() {
        log.info("原数据");
        userList.stream().forEach(System.out::println);

        log.info("将 User 转化为 String ，值来源于User::getName");
        userList.stream().map(User::getName).forEach(System.out::println);
    }


    @Test
    public void flatMap() {
        log.info("原数据");
        userList.stream().forEach(System.out::println);

        log.info("将 User 转化为 String ，值来源于User::getName");
        log.info("使用flatMap 重复上面操作");
        userList.stream().flatMap(item -> Stream.of(item.getName())).forEach(System.out::println);
    }

    @Test
    public void mapAndFlatMap() {
        log.info("原数据");
        userList.stream().forEach(System.out::println);

        log.info("体现 Map 和 flatMap 差异");
        userList.stream().map(item -> item.getRoles()).forEach(System.out::println);
        userList.stream().flatMap(item -> item.getRoles().stream()).forEach(System.out::println);
    }

    @Test
    public void find() {

        List<Double> collect = Stream.generate(Math::random).limit(999999).distinct().collect(Collectors.toList());
        Optional<Double> first = collect.stream().findFirst();
        log.info("findFirst => {}", first);

        Optional<Double> any = collect.stream().findAny();
        log.info("findAny => {}", any);
    }

    @Test
    public void match() {
        log.info("原数据");
        userList.stream().forEach(System.out::println);

        log.info("判断是否都已成年,{}", userList.stream().allMatch(item -> item.getAge() >= 18));
        log.info("判断是否有19岁以上的,{}", userList.stream().anyMatch(item -> item.getAge() >= 19));
        log.info("判断是未成年的,{}", userList.stream().noneMatch(item -> item.getAge() < 18));
    }

    @Test
    public void reduce() {
        List<Integer> collect = Stream.iterate(0, integer -> integer + 1).limit(10).collect(Collectors.toList());

        log.info("原数据");
        collect.stream().forEach(System.out::println);

        log.info("总计：{}", collect.stream().reduce((a, b) -> a + b));
        log.info("总计：{}", collect.stream().skip(10).reduce((a, b) -> a + b));
        log.info("总计：{}", collect.stream().skip(10).reduce(0, (a, b) -> a + b));

        log.info("原数据2");
        userList.stream().forEach(System.out::println);
        // 注意：这个写法无法通过编译，Stream的实现类型是 User，而累进器返回的类型是Integer，所以这需要另外使用Integer::sum 做组合操作。
        // userList.stream().reduce(0, (a, b) -> a + b.getAge())
        log.info("总计年龄：{}", userList.stream().reduce(0, (a, b) -> a + b.getAge(), Integer::sum));

        log.info("并行总计：{}", collect.parallelStream().reduce((a, b) -> a + b));
    }

    @Test
    public void option() {

        Optional<Object> empty = Optional.empty();
        log.info("创建一个空的 Optional ==> {}", empty);
        Optional<String> s = Optional.of("1");
        log.info("创建一个有值的 Optional ==> {}", s);
        // 传入null值会抛出 java.lang.NullPointerException
        // Optional<String> s2 = Optional.of(null);
        // log.info("创建一个为null的 Optional ==> {}", s2);
        Optional<Object> o = Optional.ofNullable(null);
        log.info("创建一个为null的 Optional ==> {}", o);
    }

    @Test
    public void optionEmpty() {
        Optional<Object> empty = Optional.empty();
        log.info("isPresent => {}", empty.isPresent());


        Optional<String> s = Optional.of("1");
        s.ifPresent(item -> {
            log.info("我的值 {}", item);
        });
    }

    @Test
    public void optionGet() {
        Optional<String> s = Optional.of("1");
        log.info("创建一个有值的 Optional ==> {}", s);
        Optional<Object> o = Optional.ofNullable(null);
        log.info("创建一个为null的 Optional ==> {}", o);

        try {
            log.info("get => {}", s.get());
            log.info("get => {}", o.get());
        } catch (Exception ex) {
            log.error("null get =>{}", ex.getMessage());
        }
        log.info("orElse => {}", s.orElse(pp("2")));
        log.info("orElseGet => {}", s.orElseGet(() -> {
            log.info(" 我被执行了 s1");
            return "s1";
        }));

        log.info("orElse => {}", o.orElse(2));
        log.info("orElseGet => {}", o.orElseGet(() -> {
            log.info("s2");
            return "s2";
        }));


        o.orElseThrow(() -> {
            return new RuntimeException("空的异常");
        });
    }


    @Test
    public void optionTest() {
        Optional<String> s = Optional.of("2");
        log.info("原数据 {}", s);
        Optional<String> s1 = s.filter(item -> {
            if (item.equalsIgnoreCase("1")) {
                return true;
            } else {
                return false;
            }
        });
        log.info("filter {}", s1);

        Optional<Integer> integer = s.map(item -> {
            return Integer.valueOf(item);
        });
        log.info("map {}", integer);

        Optional<Integer> integer1 = s.flatMap(item -> {
            return Optional.of(Integer.valueOf(item));
        });
        log.info("flatMap {}", integer1);

    }

    private String pp(String str) {
        log.info("我被执行了");
        return str;
    }

    @Test
    public void Streamttt() {
        Stream<User> stream = userList.stream();
        log.info("终端操作 count => {}", stream.count());
        log.info("再执行终端操作 foreach");
        stream.forEach(System.out::println);
    }

    @Test
    public void collect() {
        log.info("原数据");
        userList.stream().forEach(System.out::println);

        log.info("转化成age并收集成list");
        userList.stream().map(User::getAge).collect(Collectors.toList()).forEach(System.out::println);
    }
}
