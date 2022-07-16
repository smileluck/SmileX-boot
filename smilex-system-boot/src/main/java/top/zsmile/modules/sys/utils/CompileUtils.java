package top.zsmile.modules.sys.utils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

public class CompileUtils {

    public static void main(String[] args) throws IOException {
        String clazzName = "top.zsmile.modules.search.test";

        String str = "public class HelloWorld{\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\tSystem.out.println(\"helloworld\");\n" +
                "\t}\n" +
                "}";
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        File test = new File("D:\\test\\code\\HelloWorld.java");

        BufferedWriter bw = new BufferedWriter(new FileWriter(test));
        try {
            bw.write(str);
            bw.flush();
        } finally {
            bw.close();
            test.deleteOnExit();//程序退出时删除临时文件
        }
        int result = javaCompiler.run(null, null, null, test.getCanonicalPath());
        System.out.println(result == 0 ? "编译成功" : "编译失败");
//        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        //Runtime调用编译类
        Runtime run=Runtime.getRuntime();
        Process process=run.exec("java -cp D:\\test\\code HelloWorld");
        InputStream in=process.getInputStream();
        BufferedReader bw1=new BufferedReader(new InputStreamReader(in));
        String info="";
        while((info=bw1.readLine())!=null){
            System.out.println(info);
        }
    }

}
