package top.zsmile.modules.open.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Api(tags = "开放Git webHook接口")
@RestController
@Slf4j
@RequestMapping("/open/git")
public class OpenGitController {

    @RequestMapping("/webhook")
    public String webhook(@RequestBody JSONObject jsonObject, HttpServletRequest httpServletRequest) {

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        log.info("headerNames==>{}", headerNames);
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String header = httpServletRequest.getHeader(name);
            log.info("header {} ==> {}", name, header);
        }
        log.info("payload ==> {}", jsonObject);


        return "SUCCESS";
    }

}
