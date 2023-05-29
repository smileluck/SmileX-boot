package top.zsmile.system.boot.modules.open.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.core.api.R;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.Page;
import top.zsmile.system.boot.modules.blog.service.*;
import top.zsmile.system.boot.modules.open.entity.dto.BlogArticleCommonDto;
import top.zsmile.system.boot.modules.open.entity.dto.BlogArticleDetailDto;
import top.zsmile.system.boot.modules.open.entity.dto.BlogArticleDto;
import top.zsmile.system.boot.modules.open.entity.vo.BlogArticleLNVo;
import top.zsmile.system.boot.modules.open.entity.vo.BlogArticleTopVo;
import top.zsmile.system.boot.modules.open.entity.vo.BlogArticleVo;
import top.zsmile.system.boot.modules.open.entity.vo.BlogTagVo;
import top.zsmile.common.core.utils.AssertUtils;

import javax.annotation.Resource;
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

    @Resource
    private TempService1 tempService1;


    @RequestMapping("/test")
    public R test() {
        tempService1.addDict();
        return R.success();
    }

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
    public R<List<BlogTagVo>> tags(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("tenantId", tenantId);
        map.put("enableFlag", 1);
        List list = blogTagService.listMapByMap(map, "id", "tagName");
//        List<BlogTagVo> list = blogTagService.getRandomTagList(tenantId);
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
     * 获取文章排行帮
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("获取文章排行榜")
    @GetMapping("/{tenantId}/article/top")
    public R<List<BlogArticleTopVo>> articleTop(@ApiParam(name = "tenantId", value = "租户ID", required = true) @PathVariable Long tenantId,
                                                @Valid BlogArticleDto blogArticleDto) {
        blogArticleDto.setTenantId(tenantId);
        blogArticleDto.setPublishFlag(1);
        List<BlogArticleTopVo> list = blogArticleService.getTopList(blogArticleDto);
        return R.success(list);
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
