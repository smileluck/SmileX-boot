package top.zsmile.test.basic.serialization;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Slf4j
@SpringBootTest
public class ReflexTest {

    @Test
    public void test1() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Singleton instance = Singleton.getInstance();
//        Singleton singleton = Singleton.class.newInstance();
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton singleton = constructor.newInstance();
        log.info("instance==singleton ==> {}", instance == singleton);
    }


    @Test
    public void test2() throws Exception {
        Singleton singleton = Singleton.getInstance();
        try {
            //序列化
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
            objectOutputStream.writeObject(singleton);
            objectOutputStream.close();

            // 反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
            Singleton newClazz = (Singleton) objectInputStream.readObject();
            log.info("反序列化后对象：{}", newClazz);
            objectInputStream.close();
            log.info("newClazz==singleton ==> {}", newClazz == singleton);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void test3() {
        Integer num = 1;
        System.out.println(++num);
    }
}


@Data
class Singleton implements Serializable {
    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // 防止序列化破坏单例
    private Object readResolve(){
        return getInstance();
    }
}
