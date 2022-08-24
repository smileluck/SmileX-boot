package top.zsmile.modules.open.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zsmile.core.api.R;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.blog.service.BlogArticleService;
import top.zsmile.modules.blog.service.BlogSectionService;
import top.zsmile.modules.blog.service.BlogTagService;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
import top.zsmile.modules.open.entity.vo.BlogArticleVo;
import top.zsmile.modules.open.entity.vo.BlogTagVo;

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

    @Autowired
    private BlogArticleService blogArticleService;

    /**
     * 栏目列表
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("栏目列表")
    @GetMapping("/{tenantId}/section/list")
    public R<List> section(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId) {
        Map<String, Object> map = Collections.singletonMap("tenantId", tenantId);
        List<Map<String, Object>> list = blogSectionService.listMapByMap(map, "id", "parentId", "sectionName","type","routeUrl");
        return R.success(list);
    }

    /**
     * 标签云
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("标签云")
    @GetMapping("/{tenantId}/tag/list")
    public R<List> tags(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("tenantId", tenantId);
        map.put("enableFlag", 1);
        List<Map<String, Object>> list = blogTagService.listMapByMap(map, "id", "tagName");
        return R.success(list);
    }

    /**
     * 文章列表
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("首页文章列表")
    @GetMapping("/{tenantId}/article/home")
    public R<IPage<BlogArticleVo>> articleList(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId, Page page, BlogArticleDto blogArticleDto) {
        blogArticleDto.setTenantId(tenantId);
        blogArticleDto.setPublishFlag(1);
//        Map<String, Object> map = new HashMap<>(2);
//        map.put("tenantId", tenantId);
//        map.put("publishFlag", 1);
//        List<Map<String, Object>> list = blogArticleService.listMapByMap(map, "id", "sectionId", "poster", "tagIds", "tagNames", "articleTitle", "articleDigest", "createTime", "visitType");
        IPage<BlogArticleVo> list = blogArticleService.getListBySearch(page, blogArticleDto);
        return R.success(list);
    }

}
