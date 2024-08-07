package top.zsmile.system.boot.modules.blog.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import top.zsmile.common.core.api.R;
import top.zsmile.common.log.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.system.boot.modules.blog.entity.BlogTagEntity;
import top.zsmile.system.boot.modules.blog.entity.dto.BlogArticlePublishDto;
import top.zsmile.system.boot.modules.blog.entity.dto.BlogArticleTopDto;
import top.zsmile.system.boot.modules.blog.service.BlogArticleService;
import top.zsmile.system.boot.modules.blog.entity.BlogArticleEntity;
import top.zsmile.system.boot.modules.blog.service.BlogTagService;

/**
 * 租户博客文章
 */
@Tag(name  = "租户博客文章")
@RestController
@RequestMapping("/blog/article")
public class BlogArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogTagService blogTagService;

    @Operation(summary="查询列表（分页）")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("blog:article:list")
    @GetMapping("/list")
    public R<IPage<BlogArticleEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = blogArticleService.getPageByMap(params, "id", "tenantId", "sectionId", "tagIds", "articleTitle", "articleContent", "grammarType", "visitType", "publishFlag", "topFlag", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", page);
    }

    @Operation(summary="根据Id查询信息")
    @RequiresPermissions("blog:article:info")
    @GetMapping("/info/{id}")
    public R<BlogArticleEntity> info(@PathVariable("id") Long id) {
        BlogArticleEntity info = blogArticleService.getById(id, "id", "tenantId", "sectionId", "tagIds", "articleTitle", "articleContent", "grammarType", "visitType", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", info);
    }


    @Operation(summary="根据Id更新信息")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("blog:article:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogArticleEntity blogArticleEntity) {
        blogArticleService.updateById(blogArticleEntity);
        return R.success("修改成功");
    }

    @Operation(summary="根据id列表批量删除")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("blog:article:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        blogArticleService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @Operation(summary="保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("blog:article:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogArticleEntity blogArticleEntity) {
        String[] tagIds = blogArticleEntity.getTagIds().split(",");
        List<BlogTagEntity> tagEntities = blogTagService.listByIds(Arrays.asList(tagIds), "tagName");
        String tagNames = tagEntities.stream().map(BlogTagEntity::getTagName).collect(Collectors.joining(","));
        blogArticleEntity.setTagNames(tagNames);
        blogArticleService.saveArticle(blogArticleEntity);
        return R.success("添加成功");
    }


    @Operation(summary="根据Id置顶或取消置顶文章")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "置顶")
    @RequiresPermissions("blog:article:update")
    @PostMapping("/top")
    public R top(@Validated @RequestBody BlogArticleTopDto blogArticleTopDto) {
        BlogArticleEntity blogArticleEntity = new BlogArticleEntity();
        blogArticleEntity.setId(blogArticleTopDto.getId());
        blogArticleEntity.setTopFlag(blogArticleTopDto.getTopFlag());
        blogArticleService.updateById(blogArticleEntity);
        return R.success(blogArticleTopDto.getTopFlag() == 0 ? "取消成功" : "置顶成功");
    }

    @Operation(summary="根据Id发布或撤回文章")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "发布")
    @RequiresPermissions("blog:article:update")
    @PostMapping("/publish")
    public R publish(@Validated @RequestBody BlogArticlePublishDto blogArticlePublishDto) {
        BlogArticleEntity blogArticleEntity = new BlogArticleEntity();
        blogArticleEntity.setId(blogArticlePublishDto.getId());
        blogArticleEntity.setPublishFlag(blogArticlePublishDto.getPublishFlag());
        blogArticleService.updateById(blogArticleEntity);
        return R.success(blogArticlePublishDto.getPublishFlag() == 0 ? "取消成功" : "发布成功");
    }
}
