package top.zsmile.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class ZipFileEntity {
    private String filePath;
    private File file;
}
