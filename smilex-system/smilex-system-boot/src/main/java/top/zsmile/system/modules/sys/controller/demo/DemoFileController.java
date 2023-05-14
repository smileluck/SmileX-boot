package top.zsmile.system.modules.sys.controller.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.multipart.MultipartFile;
import top.zsmile.api.system.common.FileUploadApi;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 示例：文件上传
 */
@Api(tags = "示例：文件上传")
@RestController
@RequestMapping("/sys/demo/file")
public class DemoFileController {

    @Autowired
    private FileUploadApi fileUploadApi;

    @ApiOperation("单文件上传")
    @PostMapping("/simple")
    @RequiresPermissions("sys:demo:all")
    public R simpleUpload(@ApiParam(value = "文件") @RequestPart("file") MultipartFile multipartFile) {
        System.out.println(multipartFile);
        String path = fileUploadApi.doUpload(multipartFile);
        return R.success("上传成功", path);
    }


    @ApiOperation("多文件上传")
    @PostMapping("/multi")
    @RequiresPermissions("sys:demo:all")
    public R multiUpload(@ApiParam(value = "文件") @RequestPart("file[]") MultipartFile[] multipartFiles) {
        List<String> paths = new ArrayList<>(multipartFiles.length);
        Arrays.stream(multipartFiles).parallel().forEach(item -> {
            String path = fileUploadApi.doUpload(item);
            paths.add(path);
        });
        return R.success("上传成功", paths);
    }


}
