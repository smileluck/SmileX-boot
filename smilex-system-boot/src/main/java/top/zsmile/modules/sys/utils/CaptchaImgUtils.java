package top.zsmile.modules.sys.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

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
    private static final int height = 30;

    /**
     * 定义干扰数量
     */
    private static final int COUNT = 200;

    /**
     * 干扰线的长度=1.414*lineWidth
     */
    private static final int LINE_HEIGHT = 2;

    /**
     * 椭圆大小=1.414*lineWidth
     */
    private static final int OVAL_SIZE = 10;

    /**
     * 图片格式
     */
    private static final String IMG_FORMAT = "JPEG";

    /**
     * base64 图片前缀
     */
    private static final String BASE64_PREFIX = "data:image/jpg;base64,";


    public static void generate(String code, HttpServletResponse httpServletResponse) throws IOException {
        BufferedImage bufferedImage = generateCaptcha(code);
        ImageIO.write(bufferedImage, IMG_FORMAT, httpServletResponse.getOutputStream());
    }

    /**
     * 生成验证码 BASE64
     *
     * @return
     */
    public static String generate(String code) throws IOException {
        BufferedImage bufferedImage = generateCaptcha(code);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        //写入流中
        ImageIO.write(bufferedImage, IMG_FORMAT, byteStream);
        //转换成字节
        byte[] bytes = byteStream.toByteArray();
        //转换成base64串
        String base64 = Base64.getEncoder().encodeToString(bytes).trim();
        base64 = base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n

        return BASE64_PREFIX + base64;
    }


    /**
     * 生成验证码
     *
     * @return
     */
    private static BufferedImage generateCaptcha(String code) {
        //创建bufferImage对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        //获取Graphics2D 绘制对象
        Graphics2D graphics = bufferedImage.createGraphics();
        //设置文件
        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        // 设置颜色
        Color black = Color.black;

        graphics.setBackground(Color.WHITE);

        // 开始绘制
        graphics.fillRect(0, 0, width, height);

        final Random random = new Random();
        //画干扰线
        graphics.setColor(getRandColor(180, 230));
        for (int i = 0; i < COUNT; i++) {
            int x = random.nextInt(width - LINE_HEIGHT);
            int y = random.nextInt(height - LINE_HEIGHT);
            int x1 = random.nextInt(LINE_HEIGHT);
            int y1 = random.nextInt(LINE_HEIGHT);
            graphics.drawLine(x, y, x + x1, y + y1);
        }
//        // 画椭圆形
//        for (int i = 0; i < COUNT; i++) {
//            int x = random.nextInt(width);
//            int y = random.nextInt(height);
//            graphics.drawOval(x, y, OVAL_SIZE, OVAL_SIZE);
//        }

        //画验证码
        for (int i = 0; i < code.length(); i++) {
            // 设置到绘制对象中
            graphics.setColor(black);
            graphics.setFont(font);
            graphics.drawString(String.valueOf(code.charAt(i)), (23 * i) + 10, 24 + (i % 2 == 0 ? -i : i));
        }
        // 图象生效
        graphics.dispose();
        return bufferedImage;
    }

    /**
     * 获取rgb 0-255范围内随机颜色
     *
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) { // 取得给定范围随机颜色
        final Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }

        final int r = fc + random.nextInt(bc - fc);
        final int g = fc + random.nextInt(bc - fc);
        final int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        try {
            String s = generate("abcd");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
