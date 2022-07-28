package top.zsmile.modules.open.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;
import top.zsmile.modules.blog.service.BlogSectionService;
import top.zsmile.modules.blog.service.BlogTagService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "开放博客接口")
@RestController
@RequestMapping("/open/blog")
public class OpenBlogController {

    @Autowired
    private BlogSectionService blogSectionService;

    @Autowired
    private BlogTagService blogTagService;

    /**
     * 栏目列表
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("栏目列表")
    @GetMapping("/section/{tenantId}")
    public R section(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId) {
        Map<String, Object> map = Collections.singletonMap("tenantId", tenantId);
        List<Map<String, Object>> list = blogSectionService.getMapByMap(map, "id", "parentId", "sectionName");
        return R.success(list);
    }

    /**
     * 标签云
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("标签云")
    @GetMapping("/tags/{tenantId}")
    public R tags(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("tenantId", tenantId);
        map.put("enableFlag", 1);
        List<Map<String, Object>> list = blogTagService.getMapByMap(map, "id", "tagName");
        return R.success(list);
    }
}
