package top.zsmile.common.file.cloud;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;
import top.zsmile.common.file.constant.FileConstant;

import java.io.InputStream;
import java.util.Date;

public abstract class CloudStoreService {

    /**
     * 文件路径+名称
     *
     * @param prefix 前缀
     * @return 返回上传路径
     */
    public String getPath(String prefix) {
        //文件路径
        String path = getSimplePath(prefix);
        path += getName();
        return path;
    }

    /**
     * 纯文件夹路径
     *
     * @param prefix 前缀
     * @return 返回上传路径
     */
    public String getSimplePath(String prefix) {
        //文件路径
        Date date = new Date();
        String path = DateFormatUtils.format(date, "yyyyMMdd/HH/");
//        path += RandomStringUtils.randomAlphabetic(6) + "_" + date.getTime() / 1000;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path;
    }

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    public String getName() {
        return getName(new Date(), null);
    }

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    public String getName(Date date) {
        return getName(date, null);
    }

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    public String getName(String suffix) {
        return getName(new Date(), suffix);
    }

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    public String getName(Date date, String suffix) {
        String str = RandomStringUtils.randomAlphabetic(6) + "_" + date.getTime() / 1000;
        if (StringUtils.isNotBlank(suffix)) {
            return str + suffix;
        } else {
            return str;
        }
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
