package top.zsmile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zsmile.modules.sys.dao.SysDictDao;
import top.zsmile.modules.sys.entity.SysDictEntity;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmileSystemApplication.class)
public class TestSystemApplication {

    @Autowired
    private SysDictDao sysDictDao;

    @Test
    public void testDict() {
//        SysDictEntity sysDictEntity = new SysDictEntity();
//        sysDictEntity.setDictName("11");
//        sysDictEntity.setId(1L);
//        sysDictDao.insert(sysDictEntity);
//        sysDictDao.updateById(sysDictEntity);
//        System.out.println(sysDictEntity);

        System.out.println(System.currentTimeMillis());
        SysDictEntity sysDictEntity = sysDictDao.selectById(1, "id", "dictName");
        System.out.println(System.currentTimeMillis());


        ImmutableList<Integer> of = ImmutableList.of(1, 2, 3);
        System.out.println(System.currentTimeMillis());
        List<SysDictEntity> sysDictEntities = sysDictDao.selectBatchIds(of);
        System.out.println(System.currentTimeMillis());
//        System.out.println(sysDictEntity);

//        ImmutableList<Integer> of = ImmutableList.of(1, 2, 3);
        System.out.println(System.currentTimeMillis());
        List<SysDictEntity> sysDictEntities2 = sysDictDao.selectBatchIds(of);
        System.out.println(System.currentTimeMillis());


        System.out.println(System.currentTimeMillis());
        ImmutableMap<String, Object> map = ImmutableMap.of("dictCode", "test2", "dictName", 12);
        List<SysDictEntity> sysDictEntities1 = sysDictDao.selectByMap(map);
        System.out.println(System.currentTimeMillis());

    }
}
