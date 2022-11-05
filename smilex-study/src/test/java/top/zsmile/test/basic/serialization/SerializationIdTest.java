package top.zsmile.test.basic.serialization;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@Slf4j
@SpringBootTest
public class SerializationIdTest {

    @Test
    public void serializationTest() {
        SerializationIdClazz serializationClazz = new SerializationIdClazz("SerializationIdClazz", 20);
        try {
            //序列化
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
            objectOutputStream.writeObject(serializationClazz);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deserializationTest() {
        try {
            // 反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
            SerializationIdClazz newClazz = (SerializationIdClazz) objectInputStream.readObject();
            log.info("反序列化后对象：{}", newClazz);
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

}



@Slf4j
@Data
class SerializationIdClazz implements Serializable {

    //    private static String staticVar = "123";
//    private static final Long serialVersionUID = 999L;
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private int sex;

    /**
     * 构造器
     */
    public SerializationIdClazz(String name) {
        log.info("SerializationIdClazz Construct：name={}", name);
        this.name = name;
    }

    public SerializationIdClazz(String name, Integer age) {
        log.info("SerializationIdClazz Construct：name={},age={}", name, age);
        this.name = name;
        this.age = age;
    }

//    public SerializationIdClazz(String name, Integer age, int sex) {
//        log.info("SerializationIdClazz Construct：name={},age={},sex={}", name, age, sex);
//        this.name = name;
//        this.age = age;
//        this.sex = sex;
//    }
}
