package top.zsmile.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zsmile.SmileSystemApplication;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.entity.SysDictItemEntity;
import top.zsmile.modules.sys.service.SysDictService;
import top.zsmile.mybatis.meta.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/24/15:12
 * @ClassName: WrapperTest
 * @Description: WrapperTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmileSystemApplication.class)
public class WrapperTest {

    @Resource
    private SysDictService sysDictService;

    @Test
    public void testQueryWrapper() {
        QueryWrapper<SysDictEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "dict_code");
        wrapper.eq("id", 1);
        String sqlFragment = wrapper.getSqlFragment();

        List<SysDictEntity> list = sysDictService.list(wrapper);
        System.out.println(wrapper);
        System.out.println(sqlFragment);
        System.out.println(list);
    }
}
