package top.zsmile.cloud.sys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.cloud.sys.service.TempService1;
import top.zsmile.common.core.api.R;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.entity.SysUserEntity;
import top.zsmile.modules.sys.service.SysUserService;

import javax.annotation.Resource;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/16/15:23
 * @ClassName: TempController
 * @Description: TempController
 */
@RefreshScope
@RestController
@RequestMapping("/temp")
@Tag(name = "测试模块")
public class TempController {

//    @Resource
//    private DataSourceProperties DataSourceProperties;

    @Resource
    private TempService1 tempService1;


    @Operation(summary = "描述1")
    @GetMapping("/test")
    public R<SysDictEntity> test() {
        tempService1.addDict();
        return R.success();
    }
}
