[toc]

---

# 前言

- 什么是序列化和反序列化

    - Serializatable(序列化)：将 Java 对象转化为字节序列进行传输/保存的过程。
    - DeSerialization(反序列化)：将字节序列转化为 Java 对象。
- 意义
    - 序列化机制允许将实现序列化机制的Java 对象准换回字节序列，这些字节序列可以保存到磁盘上，也通过通过网络传输后，再转换回原来的对象。
    - 该机制使得对象可以脱离程序运行而独立存在。
- 常见用途
    1. 将对象的字节序列保存到磁盘上。 **建议：程序里的每个 JavaBean 对象都实现 Serializable 接口**
    2. 在网络通信中传输对象的字节序列。

# 实现

Java 中，对象如果想要实现序列化，必须要实现以下两个接口之一：

- Serializable
- Externalizable

## Serializable

Serializable 的序列化和反序列化大致有以下4种，后3种可以提供不同程度（越来越灵活）的序列化。

1. 通过 ObjectOutputStream 和 ObjectInputStream 默认的序列化和反序列化方式
2. 使用 transient 关键字，可选序列化的字段。
3. Serializable 序列化类定义了 writeObject(ObjectOutputStream out) 和 readObject(ObjectInputStream in)，则通过类定义的方法进行操作。
   - private void writeObject(java.io.ObjectOutputStream out) throws IOException；
   - private void readObject(java.io.ObjectIutputStream in) throws IOException,ClassNotFoundException;
   - private void readObjectNoData() throws ObjectStreamException; 
4. Serializable 序列化类定义了 writeReplace 和 readResolve 方法。
	- ANY-ACCESS-MODIFIER Object writeReplace() throws ObjectStreamException;
    - ANY-ACCESS-MODIFIER Object readResolve() throws ObjectStreamException; 

### 默认方式

```java
// Test Class 

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
    private int sex;

    private final String fff = "1";

    private void writeObject(ObjectOutputStream out) throws IOException {
        log.info("writeObject");
        //将名字反转写入二进制流
        out.writeObject(new StringBuffer(this.name).reverse().toString());
        out.writeInt(age);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        log.info("readObject");
        this.name = (String) in.readObject();
        this.age = in.readInt();
    }


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

// Test Function
@Test
public void SerializationTest() {
    SerializationClazz serializationClazz = new SerializationClazz("Serialization", 20);
    try {
        // 序列化
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
```

我们可以看到：

- 这个对象的所有属性（包括private和引用的属性），除了static和final修饰的，都可以被序列化和反序列化。

### transient 关键字

>  **使用transient关键字选择不需要序列化的字段。** 

```java
// Test Class 

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

    private void writeObject(ObjectOutputStream out) throws IOException {
        log.info("writeObject");
        //将名字反转写入二进制流
        out.writeObject(new StringBuffer(this.name).reverse().toString());
        out.writeInt(age);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        log.info("readObject");
        this.name = (String) in.readObject();
        this.age = in.readInt();
    }


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

// Test Function
@Test
public void SerializationTest() {
    SerializationClazz serializationClazz = new SerializationClazz("Serialization", 20);
    try {
        // 序列化
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
```

从输出结果，我们可以看出transient修饰的sex属性，Java 序列化的时候，会忽略该字段属性。 **对于引用类型，值是null；基本类型，值是0；boolean类型，值是false。** 

使用 transient 关键字，我们是能很方便的忽略某些字段，但是有时候我们希望进行编解码或者加密时，transient 的灵活度并不能满足我们的要求。

### 序列化类重写 writeObject 和 readObject 方法

```java

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


// Test Function
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

```

- 通过 ObjectOutputStream 会调用对象的 writeObject 方法，并将要序列化的属性写入。
- 通过 InputOutputStream 会调用对象的 readObject 方法，将序列化值读取出来。需要注意的是这里需要按照写入的顺序进行读取，并且写入的规则是什么样子的，需要按写入规则反转进行读取。
- 我们没有序列化的属性，会在反序列化后填入类型的默认值。

此外，关于 **readObjectNoData 方法，我们可以看一下他的使用场景**。

1.  Serializable对象反序列化时，由于序列化与反序列化提供的类版本不同，序列化的class的super class不同于序列化时的class的super class；
2.  序列化流被篡改时，系统都会调用readObjectNoData()方法来初始化反序列化的对象。 

如果发生上述场景的时候，**没有定义readObjectNoData，那么就会初始化成类型的默认值**；如果这时定义 readObjectNoData，**那么 readObjectNoData 会替代 readObject 方法生效。**

### writeReplace 和 readResolve 方法

#### writeReplace
**在序列化时，会先调用此方法，再调用writeObject方法。此方法可将任意对象代替目标序列化对象**。

```java

/**
 * Test writeReplace
 */
@Slf4j
@Data
class SerializationClazz3 implements Serializable {
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

    private Object writeReplace() throws IOException {
        log.info("writeReplace");
        return new SerializationClazz3("writeReplace", 2, 2);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        log.info("writeObject");
        out.writeObject(this.name);
        out.writeInt(age);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        log.info("readObject");
        this.name = in.readObject().toString();
        this.age = in.readInt();
    }

    /**
     * 构造器
     */
    public SerializationClazz3(String name) {
        log.info("SerializationClazz3 Construct：name={}", name);
        this.name = name;
    }

    public SerializationClazz3(String name, Integer age) {
        log.info("SerializationClazz3 Construct：name={},age={}", name, age);
        this.name = name;
        this.age = age;
    }

    public SerializationClazz3(String name, Integer age, int sex) {
        log.info("SerializationClazz3 Construct：name={},age={},sex={}", name, age, sex);
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}


// Test Func
@Test
public void SerializationTest3() {
    SerializationClazz3 serializationClazz = new SerializationClazz3("Serialization", 222, 1);
    try {
        //序列化
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
        objectOutputStream.writeObject(serializationClazz);
        objectOutputStream.close();

        // 反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
        SerializationClazz3 newClazz = (SerializationClazz3) objectInputStream.readObject();
        log.info("反序列化后对象：{}", newClazz);
        objectInputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException exception) {
        exception.printStackTrace();
    }
}

```

输出结果：

```
SerializationClazz3 - SerializationClazz3 Construct：name=Serialization,age=222,sex=1
SerializationClazz3 - writeReplace
SerializationClazz3 - SerializationClazz3 Construct：name=writeReplace,age=2,sex=2
SerializationClazz3 - writeObject
SerializationClazz3 - readObject
SerializationTest - 反序列化后对象：SerializationClazz3(name=writeReplace, age=2, sex=0)
```

1. writeReplace先于writeObject执行
2. writeReplace返回的对象可以是任意类型

#### readResolve

**反序列化时替换反序列化出的对象，反序列化出来的对象被立即丢弃。此方法在readeObject后调用。** 

```java


/**
 * Test readResolve
 */
@Slf4j
@Data
class SerializationClazz4 implements Serializable {
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
    
    private Object readResolve() throws IOException {
        log.info("readResolve");
        return new SerializationClazz4("readResolve", 1, 1);
    }

    /**
     * 构造器
     */
    public SerializationClazz4(String name) {
        log.info("SerializationClazz4 Construct：name={}", name);
        this.name = name;
    }

    public SerializationClazz4(String name, Integer age) {
        log.info("SerializationClazz4 Construct：name={},age={}", name, age);
        this.name = name;
        this.age = age;
    }

    public SerializationClazz4(String name, Integer age, int sex) {
        log.info("SerializationClazz4 Construct：name={},age={},sex={}", name, age, sex);
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}



// Test Func
@Test
public void SerializationTest4() {
    SerializationClazz4 serializationClazz = new SerializationClazz4("Serialization", 222, 1);
    try {
        //序列化
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
        objectOutputStream.writeObject(serializationClazz);
        objectOutputStream.close();

        // 反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
        SerializationClazz4 newClazz = (SerializationClazz4) objectInputStream.readObject();
        log.info("反序列化后对象：{}", newClazz);
        objectInputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException exception) {
        exception.printStackTrace();
    }
}
```

**readResolve常用来反序列单例类，保证单例类的唯一性。**

**注意：**

- **readResolve与writeReplace的访问修饰符可以是private、protected、public，如果父类重写了这两个方法，子类都需要根据自身需求重写，这显然不是一个好的设计。**
- **通常建议对于final修饰的类重写readResolve方法没有问题；否则，重写readResolve使用private修饰。**

### 综合使用

```java
/**
 * Test all
 */
@Slf4j
@Data
class SerializationClazz5 implements Serializable {
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

    private Object writeReplace() throws IOException {
        log.info("writeReplace");
        return new SerializationClazz5("writeReplace", 2, 2);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        log.info("writeObject");
        out.writeObject(this.name);
        out.writeInt(age);
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        log.info("readObject");
        this.name = in.readObject().toString();
        this.age = in.readInt();
    }


    private Object readResolve() throws IOException {
        log.info("readResolve");
        return new SerializationClazz5("readResolve", 9, 9);
    }

    /**
     * 构造器
     */
    public SerializationClazz5(String name) {
        log.info("SerializationClazz5 Construct：name={}", name);
        this.name = name;
    }

    public SerializationClazz5(String name, Integer age) {
        log.info("SerializationClazz5 Construct：name={},age={}", name, age);
        this.name = name;
        this.age = age;
    }

    public SerializationClazz5(String name, Integer age, int sex) {
        log.info("SerializationClazz5 Construct：name={},age={},sex={}", name, age, sex);
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}


// Test Func
@Test
public void SerializationTest5() {
    SerializationClazz5 serializationClazz = new SerializationClazz5("Serialization", 222, 1);
    try {
        //序列化
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("out.txt"));
        objectOutputStream.writeObject(serializationClazz);
        objectOutputStream.close();

        // 反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("out.txt"));
        SerializationClazz5 newClazz = (SerializationClazz5) objectInputStream.readObject();
        log.info("反序列化后对象：{}", newClazz);
        objectInputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException exception) {
        exception.printStackTrace();
    }
}
```

输出结果：

```shell
SerializationClazz5 - SerializationClazz5 Construct：name=Serialization,age=222,sex=1
SerializationClazz5 - writeReplace
SerializationClazz5 - SerializationClazz5 Construct：name=writeReplace,age=2,sex=2
SerializationClazz5 - writeObject
SerializationClazz5 - readObject
SerializationClazz5 - readResolve
SerializationClazz5 - SerializationClazz5 Construct：name=readResolve,age=9,sex=9
SerializationTest - 反序列化后对象：SerializationClazz5(name=readResolve, age=9, sex=9)
```

1. 执行顺序readObject 先于 readResolve执行
2. 


## Externalizable

