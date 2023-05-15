package top.zsmile.system.boot.modules.app.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;
import top.zsmile.system.boot.modules.app.annotation.ApiVersion;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/17/15:32
 * @ClassName: AppDemoV1Controller
 * @Description: AppDemoV1Controller
 */
@Slf4j
@RestController
@ApiVersion(2)
@RequestMapping("/app/demo/{version}")
public class AppDemoV2Controller {

    @RequestMapping("/test")
    public R test() {
        log.info("v2 test");
        return R.success("v2 test");
    }

}