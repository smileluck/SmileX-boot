package top.zsmile.system.boot.modules.blog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.service.SysDictService;
import top.zsmile.system.boot.modules.blog.service.TempService1;
import top.zsmile.system.boot.modules.blog.service.TempService2;

import javax.annotation.Resource;

@Service
public class TempService1Impl implements TempService1 {
    @Resource
    private SysDictService sysDictService;

    @Resource
    private TempService2 tempService2;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDict() {
        SysDictEntity sysDict = new SysDictEntity();
        sysDict.setDictCode("tempCode");
        sysDict.setDictName("tempName1");
        sysDict.setRemark("临时测试");
        sysDictService.save(sysDict);
        tempService2.addDict();
        throw new SXException("测试");

    }

}
