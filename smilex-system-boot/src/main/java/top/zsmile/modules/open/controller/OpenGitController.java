package top.zsmile.modules.open.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;

@Api(tags = "开放Git webHook接口")
@RestController
@RequestMapping("/open/git")
public class OpenGitController {

    @RequestMapping("/webhook")
    public R webhook(@RequestBody JSONObject jsonObject) {

        return R.success();
    }

}
