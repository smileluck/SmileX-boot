package top.zsmile.modules.app.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;
import top.zsmile.modules.app.annotation.ApiVersion;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/17/15:32
 * @ClassName: AppDemoV1Controller
 * @Description: AppDemoV1Controller
 */
@Slf4j
@ApiVersion
@RestController
@RequestMapping("/app/demo/{version}")
public class AppDemoV1Controller {

    @RequestMapping("/test")
    public R test() {
        log.info("v1 test");
        return R.success("v1 test");
    }

    @RequestMapping("/extend")
    public R extend() {
        log.info("v1 extend");
        return R.success("v1 extend");
    }

}
