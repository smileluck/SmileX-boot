package top.zsmile.common.file.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zsmile.api.common.FileUploadApi;
import top.zsmile.common.file.cloud.CloudStoreService;
import top.zsmile.common.file.cloud.LocalCloudStoreService;
import top.zsmile.common.file.cloud.OssCloudStoreService;
import top.zsmile.common.file.config.OssConfig;
import top.zsmile.common.file.config.PathConfig;
import top.zsmile.common.file.constant.FileConstant;

import javax.annotation.Resource;

@Slf4j
@Service("fileUploadApi")
public class FileUploadApiImpl implements FileUploadApi {

    @Value("${smilex.upload.type}")
    private String type;

    @Autowired
    private OssConfig ossConfig;

    @Resource
    private PathConfig pathConfig;

    private CloudStoreService cloudStoreService;

    @Override
    public String doUpload(MultipartFile multipartFile) {
        return cloudStoreService.upload(FileConstant.UPLOAD_PREFIX_COMMON, multipartFile);
    }

    @Override
    public String doUpload(String prefix, MultipartFile multipartFile) {
        return cloudStoreService.upload(prefix, multipartFile);
    }

    @Bean(name = "cloudStoreService")
    private CloudStoreService init() {
        switch (type) {
            case FileConstant.UPLOAD_TYPE_OSS:
                // 使用阿里云OSS上传
                cloudStoreService = new OssCloudStoreService(pathConfig, ossConfig);
                break;
            case FileConstant.UPLOAD_TYPE_LOCAL:
                cloudStoreService = new LocalCloudStoreService(pathConfig);
                break;
            default:
                // 默认上传到服务器本地，只适用于单机服务
                break;
        }
        return cloudStoreService;
    }


}
