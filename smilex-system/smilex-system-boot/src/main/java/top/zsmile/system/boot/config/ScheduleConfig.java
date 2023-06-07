package top.zsmile.system.boot.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.zsmile.common.core.utils.OkHttpUtil;
import top.zsmile.system.boot.modules.blog.entity.BlogGitArticleEntity;
import top.zsmile.system.boot.modules.blog.service.BlogGitArticleService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
            LocalDateTime now = LocalDateTime.now();
            blogGitArticleEntities.stream().forEach(item -> {
                if (item.getContentUrl().endsWith(".md")) {
                    String res = OkHttpUtil.get(item.getContentUrl(), "");
                    log.info("{} RES ==> {}", item.getContentUrl(), res);
                    if (res != null) {
                        if (StringUtils.isBlank(res)) {
                            item.setUpdateFlag(0);
                            item.setAsyncTime(now);
                            blogGitArticleService.updateById(item);
                        } else {
                            JSONObject resObj = JSONObject.parseObject(res);
                            String content = resObj.getString("content");
                            if (StringUtils.isNotBlank(content)) {
                                String replace = content.replace("\n", "");
                                String decode = Base64.decodeToString(replace);
                                String name = resObj.getString("name");
                                item.setFileTitle(name.substring(0, name.lastIndexOf(".")));
                                item.setUpdateFlag(0);
                                item.setContentText(decode);
                                item.setAsyncTime(now);
                                blogGitArticleService.updateById(item);
                            } else {
                                item.setUpdateFlag(0);
                                item.setContentText(content);
                                item.setAsyncTime(now);
                                blogGitArticleService.updateById(item);
                            }
                        }
                    }
                } else {
                    item.setUpdateFlag(0);
                    item.setAsyncTime(now);
                    blogGitArticleService.updateById(item);
                }
            });
        }
    }
}
