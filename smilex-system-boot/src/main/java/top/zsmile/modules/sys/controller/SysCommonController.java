package top.zsmile.modules.sys.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.entity.SysDictItemEntity;
import top.zsmile.modules.sys.service.SysDictItemService;
import top.zsmile.modules.sys.service.SysDictService;

import java.util.List;

@Api(tags = "系统通用接口")
@RestController
@RequestMapping("/sys/common")
public class SysCommonController {

    @Autowired
    private SysDictItemService sysDictItemService;

    @ApiOperation("查询字典列表")
    @GetMapping("/dict")
    public R<List<SysDictItemEntity>> dict() {
        List<SysDictItemEntity> sysDictItemEntities = sysDictItemService.listByObj(null, "dictCode", "dictValue", "dictLabel");
        return R.success(sysDictItemEntities);
    }
}
