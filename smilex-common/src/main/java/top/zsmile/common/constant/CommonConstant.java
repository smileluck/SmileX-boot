package top.zsmile.common.constant;

public interface CommonConstant {
    // SHIRO 请求头相关信息
    public static final String S_ACCESS_TOKEN = "X-Access-Token";
    public static final String S_SIGN = "X-Sign";
    public static final String S_TIMESTAMP = "X-Timestamp";
    public static final String S_INVALID_TOKEN = "invalid token";

    // 数据字典
    public static final String SYS_DICT_CACHE = "sys:cache:dict:";

    // 验证码
    public static final String SYS_LOGIN_CAPTCHA = "sys:cache:login:captcha:";
    // 验证码过期时间
    public static final int SYS_LOGIN_CAPTCHA_EXPIRE = 120;

}
