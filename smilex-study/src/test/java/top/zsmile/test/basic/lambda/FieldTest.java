package top.zsmile.test.basic.lambda;

import com.google.common.base.Function;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
public class FieldTest {
    @Test
    public void test() {
        get(TempTest::getName);
        get(TempTest::getName);
    }

    private <T,R> void get(SFunction<T,R> fn) {
        try {
            System.out.println(fn.getClass());
            Method writeReplace = fn.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            SerializedLambda invoke = (SerializedLambda) writeReplace.invoke(fn);
            String implMethodName = invoke.getImplMethodName();
            log.info("method name：{}", implMethodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}

@Data
class TempTest {
    private String name;

    private String stuName;
}

@Data
class TempTest2 {
    private String name;

}