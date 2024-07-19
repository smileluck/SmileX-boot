package top.zsmile.modules.sys.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.common.core.api.R;
import top.zsmile.modules.sys.entity.SysDictItemEntity;
import top.zsmile.modules.sys.service.SysDictItemService;

import java.util.List;

@Tag(name  = "系统通用接口")
@RestController
@RequestMapping("/sys/common")
public class SysCommonController {

    @Autowired
    private SysDictItemService sysDictItemService;

    @Operation(summary="查询字典列表")
    @GetMapping("/dict")
    public R<List<SysDictItemEntity>> dict() {
        List<SysDictItemEntity> sysDictItemEntities = sysDictItemService.listByObj(null, "dictCode", "dictValue", "dictLabel");
        return R.success(sysDictItemEntities);
    }

//    TODO 考虑数据权限和SASS问题，暂时先不开发。
//    @Operation(summary="字典组件封装")
//    @GetMapping("/dict/select")
//    public R<List> dictSelect(@Valid SysTableSelectModel sysTableSelectModel) {
//        switch(sysTableSelectModel.getDictTable()){
//            case ""
//        }
//        return R.success();
//    }
}
