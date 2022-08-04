package top.zsmile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.sys.dao.SysDictMapper;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.service.SysDictService;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmileSystemApplication.class)
public class TestSystemApplication {

    @Autowired
    private SysDictMapper sysDictDao;

    @Autowired
    private SysDictService sysDictService;

    @Test
    public void testDict() {
//        IPage<SysDictEntity> sysDictEntityIPage = sysDictDao.selectPageByMap(new Page<SysDictEntity>(1, 10), new HashMap<>());
//        System.out.println(sysDictEntityIPage);

//        SysDictEntity sysDictEntity = new SysDictEntity();
//        sysDictEntity.setDictName("11");
//        sysDictEntity.setId(1L);
////        sysDictDao.insert(sysDictEntity);
//        sysDictDao.updateById(sysDictEntity);
//        System.out.println(sysDictEntity);

//        System.out.println(System.currentTimeMillis());
//        sysDictEntity = sysDictDao.selectById(1, "id", "dictName");
//        System.out.println(System.currentTimeMillis());
////
//        System.out.println(System.currentTimeMillis());
//        sysDictEntity = sysDictDao.selectById(2, "id", "dictName");
//        System.out.println(System.currentTimeMillis());
//
//        System.out.println(System.currentTimeMillis());
//        Map<String, Object> stringObjectMap = sysDictDao.selectMapById(1, "id", "dictName");
//        System.out.println(System.currentTimeMillis());
//
//
//        ImmutableList<Integer> of = ImmutableList.of(1, 2, 3);
//        System.out.println(System.currentTimeMillis());
//        List<SysDictEntity> sysDictEntities = sysDictDao.selectBatchIds(of);
//        System.out.println(System.currentTimeMillis());
////        System.out.println(sysDictEntity);
//
////        ImmutableList<Integer> of = ImmutableList.of(1, 2, 3);
//        System.out.println(System.currentTimeMillis());
//        List<SysDictEntity> sysDictEntities2 = sysDictDao.selectBatchIds(of);
//        System.out.println(System.currentTimeMillis());
//
//
//        System.out.println(System.currentTimeMillis());
//        ImmutableMap<String, Object> map = ImmutableMap.of("dictCode", "test2", "dictName", 12);
//        List<SysDictEntity> sysDictEntities1 = sysDictDao.selectByMap(map);
//        System.out.println(System.currentTimeMillis());

        List<SysDictEntity> sysDictEntities = sysDictDao.selectListByMap(null);
        System.out.println(sysDictEntities);
        sysDictEntities.stream().forEach(item->{
            item.setId(null);
        });
//        int i = sysDictDao.batchInsert(sysDictEntities);
//        System.out.println(i);

        boolean b = sysDictService.saveBatch(sysDictEntities);
        System.out.println(b);
    }
}
