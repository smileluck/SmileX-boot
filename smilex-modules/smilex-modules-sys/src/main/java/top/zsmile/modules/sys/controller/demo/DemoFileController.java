package top.zsmile.modules.sys.controller.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.multipart.MultipartFile;
import top.zsmile.common.file.api.FileUploadApi;
import top.zsmile.common.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 示例：文件上传
 */
@Tag(name = "示例：文件上传")
@RestController
@RequestMapping("/sys/demo/file")
public class DemoFileController {

    @Autowired
    private FileUploadApi fileUploadApi;

    @Operation(summary = "单文件上传")
    @PostMapping("/simple")
    @RequiresPermissions("sys:demo:all")
    public R simpleUpload(@Parameter(name = "file", description = "文件") @RequestPart("file") MultipartFile multipartFile) {
        System.out.println(multipartFile);
        String path = fileUploadApi.doUpload(multipartFile);
        return R.success("上传成功", path);
    }


    @Operation(summary = "多文件上传")
    @PostMapping("/multi")
    @RequiresPermissions("sys:demo:all")
    public R multiUpload(@Parameter(name = "file", description = "文件") @RequestPart("file[]") MultipartFile[] multipartFiles) {
        List<String> paths = new ArrayList<>(multipartFiles.length);
        Arrays.stream(multipartFiles).parallel().forEach(item -> {
            String path = fileUploadApi.doUpload(item);
            paths.add(path);
        });
        return R.success("上传成功", paths);
    }


}
