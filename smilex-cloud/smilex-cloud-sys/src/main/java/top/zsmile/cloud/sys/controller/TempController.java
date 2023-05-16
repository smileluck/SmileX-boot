package top.zsmile.cloud.sys.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/16/15:23
 * @ClassName: TempController
 * @Description: TempController
 */
@RestController
@RequestMapping("/temp")
public class TempController {

    @RequestMapping("/test")
    public R test(){
        return R.success();
    }
}
