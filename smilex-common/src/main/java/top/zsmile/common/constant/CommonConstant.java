package top.zsmile.common.constant;

public interface CommonConstant {

    // 超级管理员默认ID
    public static final Long SUPER_ADMIN_ID = 1L;


    // SHIRO 请求头相关信息
    public static final String X_ACCESS_TOKEN = "X-Access-Token";
    public static final String X_SIGN = "X-Sign";
    public static final String X_TIMESTAMP = "X-Timestamp";
    public static final String X_INVALID_TOKEN = "invalid token";

    // 数据字典
    public static final String SYS_DICT_CACHE = "sys:cache:dict:";

    // 验证码
    public static final String SYS_LOGIN_CAPTCHA = "sys:cache:login:captcha:";
    // 验证码过期时间
    public static final int SYS_LOGIN_CAPTCHA_EXPIRE = 120;


    /**************************SysLog 日志常量**************************/
    //查询
    public static final int SYS_LOG_OPERATE_QUERY = 1;
    //添加
    public static final int SYS_LOG_OPERATE_SAVE = 2;
    //更新
    public static final int SYS_LOG_OPERATE_UPDATE = 3;
    //删除
    public static final int SYS_LOG_OPERATE_REMOVE = 4;
    //导入
    public static final int SYS_LOG_OPERATE_IMPORT = 5;
    //导出
    public static final int SYS_LOG_OPERATE_EXPORT = 6;


    //登录
    public static final int SYS_LOG_TYPE_LOGIN = 1;
    //操作
    public static final int SYS_LOG_TYPE_OPERATE = 2;
    //定时
    public static final int SYS_LOG_TYPE_TIME = 3;
    //异常
    public static final int SYS_LOG_TYPE_ERROR = 4;

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
