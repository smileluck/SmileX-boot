package top.zsmile.common.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
//@Configuration
//@ConfigurationProperties(prefix = "smilex.upload.qiniu")
public class QiniuConifg {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
