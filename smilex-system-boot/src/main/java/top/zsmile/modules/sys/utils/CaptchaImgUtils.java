package top.zsmile.modules.sys.utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

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

    public static void generateCaptcha() {
        //创建bufferImage对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        //获取Graphics2D 绘制对象
        Graphics2D graphics = bufferedImage.createGraphics();
        //设置文件
        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        // 设置颜色
        Color black = Color.black;

        // 设置到绘制对象中
        graphics.setColor(black);
        graphics.setFont(font);
        graphics.setBackground(Color.WHITE);

        // 开始绘制
        graphics.fillRect(0, 0, width, height);
        // 获取上下文
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();

    }
}
