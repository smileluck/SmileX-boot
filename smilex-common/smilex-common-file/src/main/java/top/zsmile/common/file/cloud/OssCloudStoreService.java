package top.zsmile.common.file.cloud;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.zsmile.common.file.config.OssConfig;
import top.zsmile.common.file.config.PathConfig;
import top.zsmile.common.file.filter.FileTypeFilter;
import top.zsmile.common.core.exception.SXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 阿里云 oss上传
 */
public class OssCloudStoreService extends CloudStoreService {

    private OssConfig ossConfig;

    private OSS ossClient;

    private PathConfig pathConfig;

    public OssConfig getOssConfig() {
        return ossConfig;
    }

    public OssCloudStoreService(PathConfig pathConfig, OssConfig ossConfig) {
        this.ossConfig = ossConfig;
        this.pathConfig = pathConfig;
        this.ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKey(), ossConfig.getSecretKey());
    }

    @Override
    public String upload(String prefix, String suffix, byte[] data) {
        return upload(prefix, suffix, new ByteArrayInputStream(data));
    }

    @Override
    public String upload(String prefix, String suffix, InputStream inputStream) {
        FileTypeFilter.filterAllowType(suffix);

        String networkPath = getPath(prefix);
        if (StringUtils.isNotBlank(suffix)) {
            networkPath += suffix;
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), networkPath, inputStream);
        try {
            ossClient.putObject(putObjectRequest);
            return networkPath;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String upload(String prefix, MultipartFile multipartFile) {
        try {
            String name = multipartFile.getOriginalFilename();
            int i = name.lastIndexOf(".");
            String suffix = null;
            if (i > -1)
                suffix = name.substring(i);
            return upload(prefix, suffix, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SXException("上传失败");
        }
    }

}
