package top.zsmile.test.sensitive;


import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import top.zsmile.common.core.utils.file.SpringFileUtils;

import java.io.IOException;

public class DfaAlgorithmTest {

    @Test
    public void test() throws IOException {
        DfaAlgorithm instance = DfaAlgorithm.getInstance();
        ClassPathResource classPathResource = new ClassPathResource("sensitive/illegal.txt");
        instance.readFile(classPathResource.getFile());
        instance.check("军火炸药撒发射点爆破和杀伤燃烧弹发去委任他为");
    }
}
