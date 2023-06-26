package top.zsmile.test.jvm.bytecode;

/**
 * 局部变量表对GC的影响
 */
public class LocalVariableGcTest {
    // 用6M空间测试
    int size = 6 * 1024 * 1024;

    public void gc1() {
        byte[] a = new byte[size];
        System.gc();
    }

    public void gc2() {
        byte[] a = new byte[size];
        a = null;
        System.gc();
    }

    public void gc3() {
        {
            byte[] a = new byte[size];
        }
        System.gc();
    }

    public void gc4() {
        {
            byte[] a = new byte[size];
        }
        int c = 10;
        System.gc();
    }

    public void gc5() {
        gc1();
        System.gc();
    }

    public static void main(String[] args) {
        LocalVariableGcTest localVariableGcTest = new LocalVariableGcTest();
        localVariableGcTest.gc1();
    }
}
