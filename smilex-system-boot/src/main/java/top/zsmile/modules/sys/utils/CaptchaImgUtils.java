package top.zsmile.modules.sys.utils;

/**
 * 验证码工具
 */
public class CaptchaImgUtils {

    /**
     * 定义图形大小
     */
    private static final int width = 105;
    /**
     * 定义图形大小
     */
    private static final int height = 35;

    /**
     * 定义干扰线数量
     */
    private static final int count = 200;

    /**
     * 干扰线的长度=1.414*lineWidth
     */
    private static final int LINE_HEIGHT = 2;

    /**
     * 图片格式
     */
    private static final String IMG_FORMAT = "JPEG";

    /**
     * base64 图片前缀
     */
    private static final String BASE64_PRE = "data:image/jpg;base64,";
}
