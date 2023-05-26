package top.zsmile.common.file.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 七牛云上传
 */
public class QiniuCloudStoreService extends CloudStoreService {
    @Override
    public String upload(String prefix, String suffix, byte[] data) {
        return null;
    }

    @Override
    public String upload(String prefix, String suffix, InputStream inputStream) {
        return null;
    }

    @Override
    public String upload(String prefix, MultipartFile multipartFile) {
        return null;
    }
}
