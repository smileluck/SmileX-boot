package top.zsmile.modules.open.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Api(tags = "开放Git webHook接口")
@RestController
@Slf4j
@RequestMapping("/open/git")
public class OpenGitController {

    @PostMapping("/webhook")
    public String webhook(HttpServletRequest httpServletRequest) throws IOException {

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        log.info("headerNames==>{}", headerNames);
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String header = httpServletRequest.getHeader(name);
            log.info("header {} ==> {}", name, header);
        }
//        log.info("payload ==> {}", jsonObject);

        StringBuilder builder = new StringBuilder();
        String aux = "";

        while ((aux = httpServletRequest.getReader().readLine()) != null) {
            builder.append(aux);
        }

        log.info("body ==> {}", builder.toString());

        try {
            log.info("sha256 ==> {}", sha256Hash("qOuEvHEArNQNsIbocniY", builder.toString()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        return "SUCCESS";
    }

    public static String sha256Hash(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        byte[] var6 = array;
        int var7 = array.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            byte item = var6[var8];
            sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }
}
