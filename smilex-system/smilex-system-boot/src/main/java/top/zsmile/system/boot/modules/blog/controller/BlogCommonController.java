package top.zsmile.system.boot.modules.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.common.core.api.R;
import top.zsmile.system.boot.modules.blog.entity.BlogSectionEntity;
import top.zsmile.system.boot.modules.blog.service.BlogSectionService;
import top.zsmile.system.boot.modules.blog.service.BlogTagService;

import java.util.Collections;
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

    @ApiOperation("查询分组栏目列表")
    @GetMapping("/section")
    public R<List<BlogSectionEntity>> section(@ApiParam("栏目类型") @RequestParam(name = "type", defaultValue = "1") Integer type) {
        if (type == null) {
            type = 2;
        }
        List<BlogSectionEntity> list = blogSectionService.listByMap(Collections.singletonMap("type", type), "sectionName");
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
