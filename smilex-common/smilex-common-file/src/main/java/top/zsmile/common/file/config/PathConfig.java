package top.zsmile.common.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/01/31/9:28
 * @ClassName: UploadConfig
 * @Description: UploadConfig
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "smilex.upload.path")
public class PathConfig {
    private String local;
}
