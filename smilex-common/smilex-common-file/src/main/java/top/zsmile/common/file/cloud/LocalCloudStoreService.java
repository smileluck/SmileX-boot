package top.zsmile.common.file.cloud;

import org.springframework.web.multipart.MultipartFile;
import top.zsmile.common.file.config.PathConfig;
import top.zsmile.common.file.filter.FileTypeFilter;
import top.zsmile.core.exception.SXException;

import java.io.*;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/01/30/12:34
 * @ClassName: LocalCloudStoreService
 * @Description: 本地上传
 */
public class LocalCloudStoreService extends CloudStoreService {

    private PathConfig pathConfig;

    public LocalCloudStoreService(PathConfig pathConfig) {
        this.pathConfig = pathConfig;
    }

    @Override
    public String upload(String prefix, String suffix, byte[] data) {
        return upload(prefix, suffix, new ByteArrayInputStream(data));
    }

    @Override
    public String upload(String prefix, String suffix, InputStream inputStream) {
        FileTypeFilter.filterAllowType(suffix);

        String networkPath = getSimplePath(prefix);
//        if (StringUtils.isNotBlank(suffix)) {
//            networkPath += suffix;
//        }

        OutputStream os = null;
        try {
            String localPath = pathConfig.getLocal() + networkPath;
            byte[] bs = new byte[1024];
            int len;
            File file = new File(localPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            os = new FileOutputStream(localPath.concat(getName(suffix)));
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            return networkPath;
        } catch (Exception ex) {
            return null;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String upload(String prefix, MultipartFile multipartFile) {
        try {
            String name = multipartFile.getOriginalFilename();
            int i = name.lastIndexOf(".");
            String suffix = null;
            if (i > -1)
                suffix = name.substring(i);
            return upload(prefix, suffix, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SXException("上传失败");
        }
    }
}
