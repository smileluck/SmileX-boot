package top.zsmile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zsmile.modules.sys.dao.SysDictDao;
import top.zsmile.modules.sys.entity.SysDictEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmileSystemApplication.class)
public class TestSystemApplication {

    @Autowired
    private SysDictDao sysDictDao;

    @Test
    public void testDict() {
        SysDictEntity sysDictEntity = new SysDictEntity();
        sysDictDao.insert(sysDictEntity);
    }
}
