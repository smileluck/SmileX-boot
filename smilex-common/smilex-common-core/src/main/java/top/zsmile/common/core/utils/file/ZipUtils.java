package top.zsmile.common.core.utils.file;

import lombok.extern.slf4j.Slf4j;
import top.zsmile.common.core.domain.ZipFileEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩包工具
 */
@Slf4j
public class ZipUtils {

    private final static String FILE_SEPARATE = "/";

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
                FileInputStream fileInputStream = null;
                try {
                    if (file.getClass() == ZipFileEntity.class) {
                        fileInputStream = new FileInputStream(((ZipFileEntity) file).getFile());
                        zipEntry = new ZipEntry(rootSrc + FILE_SEPARATE + ((ZipFileEntity) file).getFilePath() + FILE_SEPARATE + ((ZipFileEntity) file).getFile().getName());
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
                    zipOutputStream.flush();
                    zipOutputStream.closeEntry();
                } finally {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (file.getClass() == ZipFileEntity.class) {
                        ((ZipFileEntity) file).getFile().delete();
                    } else {
                        ((File) file).delete();
                    }
                }
            }
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
            zipOutputStream.closeEntry();
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
        fileList.add(new ZipFileEntity("test", new File("D:\\test\\code\\java\\top\\zsmile\\modules\\common\\utils\\NameStyleUtils.java")));
        compress(zipFile, "/top/zsmile/modules", fileList);

    }
}
