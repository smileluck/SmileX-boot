package top.zsmile.system.boot.modules.blog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.datasource.annotation.DS;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.service.SysDictService;
import top.zsmile.system.boot.modules.blog.service.TempService2;

import javax.annotation.Resource;

@Service
public class TempService2Impl implements TempService2 {
    @Resource
    private SysDictService sysDictService;

    @Override
    @DS(value = "slave")
//    @Transactional(rollbackFor = Exception.class)
    public void addDict() {
        SysDictEntity sysDict = new SysDictEntity();
        sysDict.setDictCode("tempCode");
        sysDict.setDictName("tempName2");
        sysDict.setRemark("临时测试");
        sysDictService.save(sysDict);
//        throw new SXException("测试");
    }

}
