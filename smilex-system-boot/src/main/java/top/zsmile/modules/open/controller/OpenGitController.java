package top.zsmile.modules.open.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.common.utils.sign.SignUtils;
import top.zsmile.core.api.R;
import top.zsmile.modules.blog.entity.BlogGitArticleEntity;
import top.zsmile.modules.blog.service.BlogGitArticleService;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "开放Git webHook接口")
@RestController
@Slf4j
@RequestMapping("/open/git")
public class OpenGitController {


    @Value("${smilex.github.webhook.secret}")
    private String secret;

    @Resource
    private BlogGitArticleService blogGitArticleService;

    @PostMapping("/webhook")
    public String webhook(HttpServletRequest httpServletRequest) throws IOException {
        StringBuilder builder = new StringBuilder();
        String aux = "";
        while ((aux = httpServletRequest.getReader().readLine()) != null) {
            builder.append(aux);
        }
        log.info("body ==> {}", builder.toString());
        try {
            String sign = SignUtils.hmacSha256Hash(secret, builder.toString());
            String reqSign = httpServletRequest.getHeader("X-Hub-Signature-256");
//            log.info("sha256 ==> {},{}", sign, reqSign);
//            if (!sign.equalsIgnoreCase(reqSign)) {
//                return "FAILURE";
//            }

            JSONObject resObject = JSONObject.parseObject(builder.toString());
            JSONObject repository = resObject.getJSONObject("repository");
            if (repository.getInteger("id").equals(427627989)) {
                String contentsUrl = repository.getString("contents_url");
                JSONArray commits = resObject.getJSONArray("commits");
                for (int i = 0; i < commits.size(); i++) {
                    JSONObject jsonObject = commits.getJSONObject(i);
                    JSONArray added = jsonObject.getJSONArray("added");
                    JSONArray modified = jsonObject.getJSONArray("modified");
                    save(contentsUrl, added);
                    save(contentsUrl, modified);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return "FAILURE";
        }
        return "SUCCESS";
    }

    private void save(String contentsUrl, JSONArray jsonArray) {
        Map<String, Object> map = new HashMap<>();
        for (int j = 0; j < jsonArray.size(); j++) {
            String idx = jsonArray.getString(j);
            if (idx.startsWith("smilex-study/doc")) {
                String url = contentsUrl.replace("{+path}", idx);
                map.put("contentUrl", url);
                log.info("sync contentUrl ==> {}", url);
                BlogGitArticleEntity obj = blogGitArticleService.getObjByMap(map, "contentUrl", "updateFlag");
                if (obj == null) {
                    obj = new BlogGitArticleEntity();
                    obj.setContentUrl(url);
                    obj.setUpdateFlag(1);
                    blogGitArticleService.save(obj);
                } else {
                    obj.setUpdateFlag(1);
                    blogGitArticleService.updateById(obj);
                }
            }
        }
    }
}
