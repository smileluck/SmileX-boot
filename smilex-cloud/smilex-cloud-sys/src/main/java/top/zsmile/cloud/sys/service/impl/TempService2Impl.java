package top.zsmile.cloud.sys.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.cloud.sys.service.TempService1;
import top.zsmile.cloud.sys.service.TempService2;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.service.SysDictService;

import javax.annotation.Resource;

@Service
public class TempService2Impl implements TempService2 {
    @Resource
    private SysDictService sysDictService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDict() {
        SysDictEntity sysDict = new SysDictEntity();
        sysDict.setDictCode("tempCode");
        sysDict.setDictName("tempName2");
        sysDict.setRemark("临时测试");
        sysDictService.save(sysDict);
    }

}
