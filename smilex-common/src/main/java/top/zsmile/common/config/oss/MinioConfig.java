package top.zsmile.common.config.oss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Minio配置
 */
@Configuration
public class MinioConfig {
    @Value("${smilex.fileSys.minio.minioUrl}")
    private String minioUrl;
    @Value("${smilex.fileSys.minio.minioName}")
    private String minioName;
    @Value("${smilex.fileSys.minio.minioPass}")
    private String minioPass;
    @Value("${smilex.fileSys.minio.bucketName}")
    private String bucketName;
}
