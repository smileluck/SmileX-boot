package top.zsmile.test.basic.lambda;


import org.junit.Test;

import java.util.function.Supplier;

public class SupplierTest {

    @Test
    public void test() {
        String string = supplierGet(() -> {
            return "supplier test";
        });
        System.out.println(string);
    }

    private static <T> T supplierGet(Supplier<T> sup) {
        return sup.get();
    }

}
