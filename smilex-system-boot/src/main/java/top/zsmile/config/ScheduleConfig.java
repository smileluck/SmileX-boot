package top.zsmile.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.zsmile.common.utils.OkHttpUtil;
import top.zsmile.modules.blog.entity.BlogGitArticleEntity;
import top.zsmile.modules.blog.service.BlogGitArticleService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Resource
    private BlogGitArticleService blogGitArticleService;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void configureTasks() {
        Map<String, Object> stringIntegerMap = Collections.singletonMap("updateFlag", 1);
        List<BlogGitArticleEntity> blogGitArticleEntities = blogGitArticleService.listByMap(stringIntegerMap, "contentUrl");
        if (blogGitArticleEntities.size() > 0) {
            log.info("检查有{}篇文章需要更新", blogGitArticleEntities.size());
            blogGitArticleEntities.stream().forEach(item -> {
                String res = OkHttpUtil.get(item.getContentUrl(), "");
                log.info("{} RES ==> {}", item.getContentUrl(), res);
                if (res != null) {
                    JSONObject resObj = JSONObject.parseObject(res);
                    String content = resObj.getString("content");
                    if (StringUtils.isNotBlank(content)) {
                        String replace = content.replace("\n", "");
                        String decode = Base64.decodeToString(replace);
                        item.setUpdateFlag(0);
                        item.setContentText(decode);
                        item.setUpdateBy("GIT");
                        blogGitArticleService.updateById(item);
                    }
                }
            });
        }
    }
}
