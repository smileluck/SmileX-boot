package top.zsmile.modules.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;
import top.zsmile.modules.blog.entity.BlogSectionEntity;
import top.zsmile.modules.blog.service.BlogSectionService;
import top.zsmile.modules.blog.service.BlogTagService;
import top.zsmile.modules.sys.entity.SysDictItemEntity;
import top.zsmile.modules.sys.service.SysDictItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "系统通用接口")
@RestController
@RequestMapping("/blog/common")
public class BlogCommonController {

    @Autowired
    private BlogSectionService blogSectionService;

    @Autowired
    private BlogTagService blogTagService;

    @ApiOperation("查询栏目列表")
    @GetMapping("/section")
    public R<List<BlogSectionEntity>> section() {
        List<BlogSectionEntity> list = blogSectionService.listByObj(null, "sectionName");
        return R.success(list);
    }


    @ApiOperation("查询标签列表")
    @GetMapping("/tag")
    public R<List<Map<String, Object>>> tag() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("enableFlag", 1);
        map.put("delFlag", 0);
        List<Map<String, Object>> list = blogTagService.listMapByMap(map, "tagName");
        return R.success(list);
    }
}
