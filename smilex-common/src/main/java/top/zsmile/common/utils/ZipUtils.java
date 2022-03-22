package top.zsmile.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class ZipUtils {

    private final static String FILE_SEPARATE = "/";

    @Data
    @AllArgsConstructor
    public final static class ZipFileVO {
        private String filePath;
        private File file;
    }

    private static File createZip(String zipPath) {
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

    public static File createZip(String zipPath, String rootSrc, List outFiles) {
        File zip = createZip(zipPath);
        compress(zip, rootSrc, outFiles);
        return zip;
    }


    public static void compress(File zipFile, String rootSrc, List outFile) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));) {
            for (Object file : outFile) {
                ZipEntry zipEntry = null;
                FileInputStream fileInputStream;
                if (file.getClass() == ZipFileVO.class) {
                    fileInputStream = new FileInputStream(((ZipFileVO) file).getFile());
                    zipEntry = new ZipEntry(rootSrc + FILE_SEPARATE + ((ZipFileVO) file).getFilePath() + FILE_SEPARATE + ((ZipFileVO) file).getFile().getName());
                } else {
                    fileInputStream = new FileInputStream((File) file);
                    zipEntry = new ZipEntry(rootSrc + FILE_SEPARATE + ((File) file).getName());
                }
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
            zipOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File zipFile = createZip("D:\\test\\code.zip");
        List fileList = new ArrayList<>();
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\CmdUtils.java"));
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\ConvertUtils.java"));
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\MD5SignUtils.java"));
        fileList.add(new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\MD5Utils.java"));
        fileList.add(new ZipFileVO("test", new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\NameStyleUtils.java")));
        compress(zipFile, "/top/zsmile/modules", fileList);
        
    }
}
