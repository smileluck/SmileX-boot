package top.zsmile.test.basic.serialization;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@Slf4j
@SpringBootTest
public class SerializationTest {

    @Test
    public void SerializationTest() {
        SerializationClazz serializationClazz = new SerializationClazz("Serialization", 20, 1);
        try {
            //序列化
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
            objectOutputStream.writeObject(serializationClazz);
            objectOutputStream.close();

            // 反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
            SerializationClazz newClazz = (SerializationClazz) objectInputStream.readObject();
            log.info("反序列化后对象：{}", newClazz);
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    @Test
    public void SerializationTest2() {
        SerializationClazz2 serializationClazz = new SerializationClazz2("Serialization", 20, 1);
        try {
            //序列化
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
            objectOutputStream.writeObject(serializationClazz);
            objectOutputStream.close();

            // 反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
            SerializationClazz2 newClazz = (SerializationClazz2) objectInputStream.readObject();
            log.info("反序列化后对象：{}", newClazz);
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void ExternalizationTest() {
        ExternalizationClazz externalizationClazz = new ExternalizationClazz("Externalization", 30);
    }

}

@Slf4j
@Data
class ExternalizationClazz implements Externalizable {
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    /**
     * 构造器
     */
    public ExternalizationClazz(String name, Integer age) {
        log.info("ExternalizationClazz Construct：name={},age={}", name, age);
        this.name = name;
        this.age = age;
    }

}

@Slf4j
@Data
class SerializationClazz implements Serializable {
    //    private static String staticVar = "123";
    private static final Long serialVersionUID = 1L;
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
    private transient int sex;

    /**
     * 构造器
     */
    public SerializationClazz(String name) {
        log.info("SerializationClazz Construct：name={}", name);
        this.name = name;
    }

    public SerializationClazz(String name, Integer age) {
        log.info("SerializationClazz Construct：name={},age={}", name, age);
        this.name = name;
        this.age = age;
    }

    public SerializationClazz(String name, Integer age, int sex) {
        log.info("SerializationClazz Construct：name={},age={},sex={}", name, age, sex);
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}


/**
 * Test writeObject and readObject
 */
@Slf4j
@Data
class SerializationClazz2 implements Serializable {
    //    private static String staticVar = "123";
    private static final Long serialVersionUID = 1L;
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

    private void writeObject(ObjectOutputStream out) throws IOException {
        log.info("writeObject");
        //将名字反转写入
        out.writeObject(new StringBuilder(this.name).reverse());
        out.writeInt(age);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        log.info("readObject");
        this.name = ((StringBuilder) in.readObject()).reverse().toString();
        this.age = in.readInt();
    }


    /**
     * 构造器
     */
    public SerializationClazz2(String name) {
        log.info("SerializationClazz Construct：name={}", name);
        this.name = name;
    }

    public SerializationClazz2(String name, Integer age) {
        log.info("SerializationClazz Construct：name={},age={}", name, age);
        this.name = name;
        this.age = age;
    }

    public SerializationClazz2(String name, Integer age, int sex) {
        log.info("SerializationClazz Construct：name={},age={},sex={}", name, age, sex);
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
