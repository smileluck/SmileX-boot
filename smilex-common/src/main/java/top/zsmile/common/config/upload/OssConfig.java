package top.zsmile.common.config.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "smilex.upload.oss")
public class OssConfig {

//    @Value("${smilex.upload.oss.endpoint}")
    private String endpoint;
//    @Value("${smilex.upload.oss.accessKey}")
    private String accessKey;
//    @Value("${smilex.upload.oss.secretKey}")
    private String secretKey;
//    @Value("${smilex.upload.oss.bucketName}")
    private String bucketName;

}
