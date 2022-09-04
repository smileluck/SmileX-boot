package top.zsmile.modules.open.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.utils.ValidatorUtils;
import top.zsmile.core.api.R;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.blog.entity.BlogTimelineEntity;
import top.zsmile.modules.blog.service.BlogArticleService;
import top.zsmile.modules.blog.service.BlogSectionService;
import top.zsmile.modules.blog.service.BlogTagService;
import top.zsmile.modules.blog.service.BlogTimelineService;
import top.zsmile.modules.open.entity.dto.BlogArticleCommonDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDetailDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
import top.zsmile.modules.open.entity.vo.BlogArticleLNVo;
import top.zsmile.modules.open.entity.vo.BlogArticleVo;
import top.zsmile.modules.open.entity.vo.BlogTagVo;
import top.zsmile.modules.sys.entity.SysTenantEntity;
import top.zsmile.modules.sys.service.SysTenantService;
import top.zsmile.modules.sys.utils.AssertUtils;

import javax.validation.Valid;
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

    @Autowired
    private BlogTimelineService blogTimelineService;

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
        List<Map<String, Object>> list = blogSectionService.listMapByMap(map, "id", "parentId", "sectionName", "type", "routeUrl");
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

    /**
     * 获取文章内容
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("获取文章内容")
    @PostMapping("/{tenantId}/article/detail")
    public R<BlogArticleVo> articleDetail(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId,
                                          @Validated @RequestBody BlogArticleDetailDto blogArticleDto) {
        blogArticleDto.setTenantId(tenantId);
        BlogArticleVo detail = blogArticleService.getDetailById(blogArticleDto);
        AssertUtils.notNull(detail, "文章不存在");
        blogArticleService.checkPassToken(tenantId, blogArticleDto.getPassToken(), detail);
        return R.success(detail);
    }

    /**
     * 获取文章上下页
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("获取文章上下页")
    @GetMapping("/{tenantId}/article/ln")
    public R<BlogArticleLNVo> articleLn(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId,
                                        @Valid BlogArticleCommonDto blogArticleCommonDto) {
        blogArticleCommonDto.setTenantId(tenantId);
        BlogArticleLNVo blogArticleLNVo = blogArticleService.getLnArticle(blogArticleCommonDto);
        return R.success(blogArticleLNVo);
    }

    /**
     * 文章列表
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("时间线")
    @GetMapping("/{tenantId}/timeline")
    public R timeline(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId) {
        List<Map<String, Object>> list = blogTimelineService.listMapByMap(Collections.singletonMap("tenantId", tenantId), "year", "title", "description");
        return R.success(list);
    }

}
