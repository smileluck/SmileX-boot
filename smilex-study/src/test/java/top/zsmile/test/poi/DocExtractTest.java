package top.zsmile.test.poi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/03/13/11:26
 * @ClassName: DocExtractTest
 * @Description: DocExtractTest
 */

@Slf4j
@SpringBootTest
public class DocExtractTest {

    /**
     * https://github.com/mwilliamson/java-mammoth
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        File file = new File("D://test/doc/import.docx");
        if (file.exists()) {
            DocumentConverter converter = new DocumentConverter();
            Result<String> result = converter.convertToHtml(file);
            String html = result.getValue(); // The generated HTML
            Set<String> warnings = result.getWarnings(); // Any warnings during conversion
            System.out.println(html);
        }
    }
}
