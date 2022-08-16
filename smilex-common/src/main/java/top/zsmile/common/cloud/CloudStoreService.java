package top.zsmile.common.cloud;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.multipart.MultipartFile;
import top.zsmile.common.constant.CommonConstant;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public abstract class CloudStoreService {

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @return 返回上传路径
     */
    public String getPath(String prefix) {
        //文件路径
        Date date = new Date();
        String path = DateFormatUtils.format(date, "yyyyMMdd/HH/");
        path += RandomStringUtils.randomAlphabetic(6) + "_" + date.getTime() / 1000;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path;
    }

    /**
     * 文件上传
     *
     * @param prefix 文件前缀路径，包含文件名
     * @param data   文件字节数组
     * @return 返回http地址
     */
    public abstract String upload(String prefix, String suffix, byte[] data);

    /**
     * 文件上传
     *
     * @param prefix      文件前缀路径，包含文件名
     * @param inputStream 字节流
     * @return 返回http地址
     */
    public abstract String upload(String prefix, String suffix, InputStream inputStream);

    /**
     * 文件上传
     *
     * @param prefix        文件前缀路径，包含文件名
     * @param multipartFile 文件
     * @return 返回http地址
     */
    public abstract String upload(String prefix, MultipartFile multipartFile);
}
