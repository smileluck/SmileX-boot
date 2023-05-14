package top.zsmile.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zsmile.GeneratorApplication;
import top.zsmile.system.modules.generator.dao.SysDictDao;
import top.zsmile.system.modules.generator.domain.entity.SysDictEntity;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorApplication.class)
public class TempSystemApplication {

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
        SysDictEntity sysDictEntity = sysDictDao.selectById(1);
        System.out.println(System.currentTimeMillis());


        ImmutableList<Integer> of = ImmutableList.of(1, 2, 3);
        System.out.println(System.currentTimeMillis());
        List<SysDictEntity> sysDictEntities = sysDictDao.selectBatchIds(of);
        System.out.println(System.currentTimeMillis());
//        System.out.println(sysDictEntity);
        System.out.println(System.currentTimeMillis());
        List<SysDictEntity> sysDictEntities2 = sysDictDao.selectBatchIds(of);
        System.out.println(System.currentTimeMillis());

        System.out.println(System.currentTimeMillis());
        ImmutableMap<String, Object> map = ImmutableMap.of("dict_code", "test2", "dict_name", 12);
        List<SysDictEntity> sysDictEntities1 = sysDictDao.selectByMap(map);
        System.out.println(System.currentTimeMillis());

    }
}
