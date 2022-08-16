package top.zsmile.api.common;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传API
 */
public interface FileUploadApi {
    /**
     * 上传文件，默认common前缀
     *
     * @param multipartFile 文件
     * @return
     */
    String doUpload(MultipartFile multipartFile);

    /**
     * 上传文件
     *
     * @param prefix        前缀
     * @param multipartFile 文件
     * @return
     */
    String doUpload(String prefix, MultipartFile multipartFile);
}
