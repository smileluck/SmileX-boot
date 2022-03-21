package top.zsmile.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static File createZip(String zipPath) {
        File zipFile = new File(zipPath);
        if (zipFile.exists()) {
            zipFile.delete();
        }
        try {
            zipFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipFile;
    }


    public static void compress(File zipFile, String rootSrc, List<File> outFile) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));) {
            for (File file : outFile) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(rootSrc + "/" + file.getName());
                zipOutputStream.putNextEntry(zipEntry);
                int length;
                byte[] buffer = new byte[1024];
                while ((length = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, length);
                }
            }
            zipOutputStream.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compress(File zipFile, String rootSrc, File outFile) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
             FileInputStream fileInputStream = new FileInputStream(outFile);) {
//            zipOutputStream.
            ZipEntry zipEntry = new ZipEntry(rootSrc + "/" + outFile.getName());
            zipOutputStream.putNextEntry(zipEntry);
            int length;
            byte[] buffer = new byte[1024];
            while ((length = fileInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }
            zipOutputStream.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File zipFile = createZip("D:\\test\\code.zip");
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\CmdUtils.java"));
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\ConvertUtils.java"));
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\MD5SignUtils.java"));
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\MD5Utils.java"));
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\NameStyleUtils.java"));

        compress(zipFile, "/top/zsmile/modules", fileList);
    }

    public static void main1(String[] args) throws IOException {
        File zipFile = new File("D:\\test\\code.zip");
        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        ZipEntry zipEntry = new ZipEntry("/top/zsmile/modules/测试.txt");
//        zipEntry.
        zipOutputStream.putNextEntry(zipEntry);
//        zipOutputStream.finish();
        zipOutputStream.flush();
//        ZipEntry zipEntry2 = new ZipEntry("/top/zsmile/modules/测试2.txt");
//        zipOutputStream.putNextEntry(zipEntry2);
//        zipEntry.
//        zipOutputStream.putNextEntry(zipEntry);
//        zipOutputStream.finish();
    }
}
