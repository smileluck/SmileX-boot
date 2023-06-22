package top.zsmile.test.sensitive;


import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import top.zsmile.common.core.utils.file.SpringFileUtils;

import java.io.IOException;
import java.util.List;

/**
 * 敏感词dfa测试单元
 */
public class DfaAlgorithmTest {

    @Test
    public void test() throws IOException {
        DfaAlgorithm instance = DfaAlgorithm.getInstance();
        ClassPathResource classPathResource = new ClassPathResource("sensitive/illegal.txt");
        instance.readFile(classPathResource.getFile());
//        instance.check("军火炸药撒发射点爆破和杀伤燃烧弹发去委任他为");
        long startTime = System.currentTimeMillis();
        List<DfaSearchResult> results = instance.findAll("军火炸药撒发射点爆破和杀伤燃烧弹发去委任他为");

        String newStr = instance.replace("军火炸药撒发射点爆破和杀伤燃烧弹发去委任他为", '*');
        long endTime = System.currentTimeMillis();
        System.out.println("spend time : " + (endTime - startTime));

    }
}
