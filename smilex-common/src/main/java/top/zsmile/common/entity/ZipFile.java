package top.zsmile.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class ZipFile {
    private String filePath;
    private File file;
}
