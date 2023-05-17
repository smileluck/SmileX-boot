package top.zsmile.cloud.sys.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.common.core.api.R;
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
public class TempController {

//    @Resource
//    private DataSourceProperties DataSourceProperties;

    @Resource
    private SysUserService userService;

        @Value("${spring.datasource.druid.url}")
//    @NacosValue(value = "${spring.datasource.druid.url}", autoRefreshed = true)
    private String url;

    @RequestMapping("/test")
    public R test() {
        System.out.println(url);
        SysUserEntity info = userService.getById(1L);
        return R.success(info);
    }
}
