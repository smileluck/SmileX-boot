package top.zsmile.common.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Minio配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "smilex.upload.minio")
public class MinioConfig {
    //    @Value("${smilex.upload.minio.minioUrl}")
    private String minioUrl;
    //    @Value("${smilex.upload.minio.minioName}")
    private String minioName;
    //    @Value("${smilex.upload.minio.minioPass}")
    private String minioPass;
    //    @Value("${smilex.upload.minio.bucketName}")
    private String bucketName;
}
