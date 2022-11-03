package top.zsmile.test.basic.serialization;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@Slf4j
@SpringBootTest
public class ExternalizationTest {
    @Test
    public void test() {
        ExternalizationClazz clazz = new ExternalizationClazz("Test", 11);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
            objectOutputStream.writeObject(clazz);
            objectOutputStream.close();

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
            ExternalizationClazz newClazz = (ExternalizationClazz) objectInputStream.readObject();
            objectInputStream.close();

            log.info("clazz => {}", clazz);
            log.info("newClazz => {}", newClazz);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    public void writeExternal(ObjectOutput out) throws IOException {
        log.info("writeExternal");
        out.writeObject(this.name + "writeExternal");
        out.writeInt(age);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        log.info("readExternal");
        this.name = in.readObject().toString() + "readExternal";
        this.age = in.readInt();
    }

    private Object writeReplace() throws IOException {
        log.info("writeReplace");
        return new ExternalizationClazz("writeReplace", 2);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        log.info("writeObject");
        out.writeObject(this.name + "writeObject");
        out.writeInt(age);
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        log.info("readObject");
        this.name = in.readObject().toString() + "readObject";
        this.age = in.readInt();
    }


    private Object readResolve() throws IOException {
        log.info("readResolve");
        return new ExternalizationClazz("readResolve", 9);
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