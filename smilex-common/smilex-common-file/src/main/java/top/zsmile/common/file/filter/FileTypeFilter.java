package top.zsmile.common.file.filter;

import top.zsmile.core.exception.SXException;

public class FileTypeFilter {

    private static String[] filterTypes = {"jsp", "php"};

    private static String[] allowTypes = {
            ".jpg", ".jpeg", ".png",
            ".mp4", ".wmv", ".mpg", ".ram", ".rm"};

//    private static final Pattern pattern = Pattern.compile("/\\.(mp4|wmv|mpg|mpeg|ram|rm|gif|jpg|jpeg|png)$/i");

    public static void filterAllowType(String suffix) {
        if (suffix == null) {
            throw new SXException("后缀不能为空");
        }
        for (String allowType : allowTypes) {
            if (allowType.equals(suffix)) return;
        }
//        Matcher m = pattern.matcher(suffix);
//        if (m.find()) {
//            return;
//        }
        throw new SXException("该文件类型不允许上传");
    }

}
