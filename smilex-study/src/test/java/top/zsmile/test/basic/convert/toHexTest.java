package top.zsmile.test.basic.convert;

import org.junit.Test;
import top.zsmile.common.core.utils.CmdUtils;

public class toHexTest {
    @Test
    public void toHex() {
        Integer num1 = 10;
        String hex1 = Integer.toHexString(num1);
        System.out.println(hex1);

        Integer num2 = -10;
        String hex2 = Integer.toHexString(num2);
        String substring = hex2.substring(hex2.length() - 4);
        System.out.println(hex2);
        System.out.println(substring.substring(substring.length() - 4));
    }
}
