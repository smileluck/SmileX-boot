package top.zsmile.common.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.zsmile.api.common.FileUploadApi;
import top.zsmile.common.constant.CommonConstant;

@Slf4j
@Service("fileUploadApi")
public class FileUploadApiImpl implements FileUploadApi {

    @Value("${smilex.fileSys.uploadType}")
    private String uploadType;

    @Override
    public String doUpload() {
        return uploadFile();
    }

    /**
     * 上传文件
     *
     * @return
     */
    private String uploadFile() {
        // 上传到服务器本地，只适用于单机服务
        if (uploadType.equals(CommonConstant.UPLOAD_TYPE_LOCAL)) {

        } else {

        }
        return null;
    }
}
