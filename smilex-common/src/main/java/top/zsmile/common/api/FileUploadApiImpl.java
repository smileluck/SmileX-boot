package top.zsmile.common.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zsmile.api.common.FileUploadApi;
import top.zsmile.common.cloud.CloudStoreService;
import top.zsmile.common.cloud.OssCloudStoreService;
import top.zsmile.common.config.oss.OssConfig;
import top.zsmile.common.constant.CommonConstant;

import javax.annotation.PostConstruct;

@Slf4j
@Service("fileUploadApi")
public class FileUploadApiImpl implements FileUploadApi {

    @Value("${smilex.fileSys.uploadType}")
    private String uploadType;

    @Autowired
    private OssConfig ossConfig;

    @Override
    public String doUpload(MultipartFile multipartFile) {
        return cloudStoreService.upload(CommonConstant.UPLOAD_PREFIX_COMMON, multipartFile);
    }

    @Override
    public String doUpload(String prefix, MultipartFile multipartFile) {
        return cloudStoreService.upload(prefix, multipartFile);
    }

    @Bean(name = "cloudStoreService")
    public CloudStoreService init() {
        switch (uploadType) {
            case CommonConstant.UPLOAD_TYPE_OSS:
                // 使用阿里云OSS上传
                cloudStoreService = new OssCloudStoreService(ossConfig);
            default:
                // 默认上传到服务器本地，只适用于单机服务
                break;
        }
        return cloudStoreService;
    }

    private CloudStoreService cloudStoreService = null;

}
