package top.zsmile.common.utils.file;

import lombok.extern.slf4j.Slf4j;
import top.zsmile.common.utils.Asserts;
import top.zsmile.common.utils.StringPool;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FileUtils {
    /**
     * response 下载
     *
     * @param file
     * @param response
     */
    public static void downloadFile(File file, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            FileInputStream fileInputStream = new FileInputStream(file);
            inputStream = new BufferedInputStream(fileInputStream);
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }


    /**
     * 获取符合条件的路径列表
     *
     * @param searchPath 路径，支持ANT
     * @return
     */
    public static List<String> getPaths(String searchPath) {
        List<String> paths = parsePath(searchPath);
        StringBuilder strBuild = new StringBuilder();

        for (String path : paths) {
            if (path.contains("*") || path.contains("?")) {

            }
        }

        return null;
    }

    /**
     * 解析出多个文件路径。统一采用正斜杠，并将反斜杠变为正斜杠处理
     * 支持规则：
     * 1. * 通配符，
     * 2. ? 单文本字符匹配
     * <p>
     * 支持格式：
     * 1. top/zsmile\*\entity
     * 2. top/zsmile\*\entity\*.java
     * 3. top/zsmile\*\entity\Test?.java
     *
     * @return
     */
    public static List<String> parsePath(String path) {
        Asserts.isNotBlank(path, "路径为空");
        // 将反斜杠转化为正斜杠后，并根据正斜杠切割。
        String[] split = path.replace(StringPool.BACK_SLASH, StringPool.SLASH).split(StringPool.SLASH);

        // 路径无法切割
        if (split.length == 1) return Collections.singletonList(split[0]);

        // 处理路径数组
        List<String> pathList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String before = split[i];
            // 前后路径不包含 * 和 ? 号时，将两个路径合并成一个
            if (before.contains("*") || before.contains("?")) {
                pathList.add(stringBuilder.toString());
                pathList.add(before);
                stringBuilder.setLength(0);
                continue;
            }
            stringBuilder.append(StringPool.SLASH);
            stringBuilder.append(before);
            if (i != split.length - 1) {
                String after = split[i + 1];
                if (after.contains("*") || after.contains("?")) {
                    continue;
                }
            } else {
                if (stringBuilder.length() > 0) {
                    pathList.add(stringBuilder.toString());
                }
            }
        }
        return pathList;
    }

}
