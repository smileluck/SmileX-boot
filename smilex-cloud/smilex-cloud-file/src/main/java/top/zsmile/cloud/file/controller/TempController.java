package top.zsmile.cloud.file.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


    @Value("${smilex.url:123}")
//    @NacosValue(value = "${spring.datasource.druid.url}", autoRefreshed = true)
    private String url;

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        System.out.println(url);
        return url;
    }
}
