package top.zsmile.common.file.constant;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/13/19:01
 * @ClassName: FileConstant
 * @Description: FileConstant
 */
public interface FileConstant {

    /**************************文件上传**************************/
    // 阿里云OSS
    public static final String UPLOAD_TYPE_OSS = "oss";
    // Minio
    public static final String UPLOAD_TYPE_MINIO = "minio";
    // Local 本地
    public static final String UPLOAD_TYPE_LOCAL = "local";

    // 默认前缀名
    public static final String UPLOAD_PREFIX_COMMON = "smilex";
    // 博客前缀
    public static final String UPLOAD_PREFIX_BLOG = "blog";
}
