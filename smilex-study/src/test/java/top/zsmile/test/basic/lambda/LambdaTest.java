package top.zsmile.test.basic.lambda;

//@SpringBootTest
public class LambdaTest {
//    @Test
//    public void lambdaCompere() {
//        String fff = "fff";
//        // 匿名函数类
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(fff);
//                System.out.println("线程输出");
//            }
//        });
//
//        // lambda
//        Thread thread1 = new Thread(() -> {
//            System.out.println(fff);
//            System.out.println("lambda线程输出");
//        });
//
//    }

    public void run(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                a=10;
                System.out.println(this);
                System.out.println("线程输出");
            }
        }).start();

        // lambda
        new Thread(() -> {
            System.out.println(this);
            System.out.println("lambda线程输出");
        }).start();
    }

    public static void main(String[] args) {
        new LambdaTest().run();
    }
}
