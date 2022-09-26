package top.zsmile.test.basic.files;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * 路径测试类
 */
@Slf4j
@SpringBootTest
public class PathTest {

    /**
     * 通配符匹配
     */
    @Test
    public void wildcard() {
        String path = ClassLoader.getSystemClassLoader().getResource("").getPath();
        System.out.println(path);

    }


    /**
     * 获取根路径
     */
    @Test
    public void getRootPath() throws FileNotFoundException {
        log.info("获取当前项目路径的地址:=====");
        String path2 = System.getProperty("user.dir");
        log.info(path2);

        log.info("\n\n获取classes路径:=====");
        log.info("方法1：通过CLassLoader获取：");
        String path = this.getClass().getClassLoader().getResource("").getPath();
        log.info(path);

        log.info("方法2：通过Spring提供的ResourceUtils方法获取:");
        String path1 = ResourceUtils.getURL("classpath:").getPath();
        log.info(path1);
    }

}
