package top.zsmile.common.config.oss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 阿里云OSS配置
 */
@Configuration
public class OssConfig {

    @Value("${smilex.fileSys.oss.endpoint}")
    private String endpoint;
    @Value("${smilex.fileSys.oss.accessKey}")
    private String accessKeyId;
    @Value("${smilex.fileSys.oss.secretKey}")
    private String accessKeySecret;
    @Value("${smilex.fileSys.oss.bucketName}")
    private String bucketName;

}
