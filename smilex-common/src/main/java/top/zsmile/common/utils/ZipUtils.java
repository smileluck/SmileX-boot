package top.zsmile.common.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {



    public static void main(String[] args) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("D:\\test\\test.zip"));
        ZipEntry zipEntry = new ZipEntry("/top/zsmile/modules/测试.txt");
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.finish();
    }
}
