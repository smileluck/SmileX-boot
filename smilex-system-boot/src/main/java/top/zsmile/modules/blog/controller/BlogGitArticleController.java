package top.zsmile.modules.blog.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import top.zsmile.core.api.R;
import top.zsmile.annotation.SysLog;
import top.zsmile.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.blog.entity.BlogTagEntity;
import top.zsmile.modules.blog.entity.dto.BlogArticlePublishDto;
import top.zsmile.modules.blog.service.BlogArticleService;
import top.zsmile.modules.blog.service.BlogGitArticleService;
import top.zsmile.modules.blog.entity.BlogGitArticleEntity;
import top.zsmile.modules.blog.service.BlogTagService;

import javax.annotation.Resource;

/**
 * 租户博客-git文章同步
 */
@Api(tags = "租户博客-git文章同步")
@RestController
@RequestMapping("/blog/git/article")
public class BlogGitArticleController {

    @Autowired
    private BlogGitArticleService blogGitArticleService;

    @Resource
    private BlogArticleService blogArticleService;

    @Resource
    private BlogTagService blogTagService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "租户博客-git文章同步", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("blog:git:article:list")
    @GetMapping("/list")
    public R<IPage<BlogGitArticleEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = blogGitArticleService.getPageByMap(params);
        return R.success("查询成功", page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("blog:git:article:info")
    @GetMapping("/info/{id}")
    public R<BlogGitArticleEntity> info(@PathVariable("id") Long id) {
        BlogGitArticleEntity info = blogGitArticleService.getById(id);
        return R.success("查询成功", info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "租户博客-git文章同步", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("blog:git:article:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogGitArticleEntity blogGitArticleEntity) {
        blogGitArticleService.updateById(blogGitArticleEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "租户博客-git文章同步", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("blog:git:article:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        blogGitArticleService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "租户博客-git文章同步", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("blog:git:article:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogGitArticleEntity blogGitArticleEntity) {
        blogGitArticleService.save(blogGitArticleEntity);
        return R.success("添加成功");
    }


    @ApiOperation("根据Id发布文章")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "发布")
    @RequiresPermissions("blog:git:article:publish")
    @PostMapping("/publish")
    public R publish(@RequestBody BlogArticleEntity blogArticleEntity) {
        String[] tagIds = blogArticleEntity.getTagIds().split(",");
        List<BlogTagEntity> tagName = blogTagService.listByIds(Arrays.asList(tagIds), "tagName");
        List<String> collect = tagName.stream().map(item -> item.getTagName()).collect(Collectors.toList());
        blogArticleEntity.setTagNames(Joiner.on(",").join(collect));
        blogArticleService.saveArticle(blogArticleEntity);
        return R.success("添加成功");
    }

}
