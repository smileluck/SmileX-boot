package top.zsmile.common.filter;

import top.zsmile.core.exception.SXException;

public class FileTypeFilter {

    private static String[] filterTypes = {"jsp", "php"};

    private static String[] allowTypes = {".jpg", ".png", ".mp4"};

    public static void filterAllowType(String suffix) {
        if (suffix == null) {
            throw new SXException("后缀不能为空");
        }
        for (String allowType : allowTypes) {
            if (allowType.equals(suffix)) return;
        }
        throw new SXException("该文件类型不允许上传");
    }

}
