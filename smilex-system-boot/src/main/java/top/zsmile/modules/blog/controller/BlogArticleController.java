package top.zsmile.modules.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import top.zsmile.annotation.SysLog;
import top.zsmile.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.blog.service.BlogArticleService;
import top.zsmile.modules.blog.entity.BlogArticleEntity;

/**
 * 租户博客文章
 */
@Api(tags = "租户博客文章")
@RestController
@RequestMapping("/blog/article")
public class BlogArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("blog:article:list")
    @GetMapping("/list")
    public R<IPage<BlogArticleEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = blogArticleService.getPageByMap(params, "id", "tenantId", "sectionId", "tagIds", "articleTitle", "articleContent", "grammarType", "visitType", "publishFlag", "topFlag", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("blog:article:info")
    @GetMapping("/info/{id}")
    public R<BlogArticleEntity> info(@PathVariable("id") Long id) {
        BlogArticleEntity info = blogArticleService.getById(id, "id", "tenantId", "sectionId", "tagIds", "articleTitle", "articleContent", "grammarType", "visitType", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("blog:article:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogArticleEntity blogArticleEntity) {
        blogArticleService.updateById(blogArticleEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("blog:article:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        blogArticleService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "租户博客文章", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("blog:article:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogArticleEntity blogArticleEntity) {
        blogArticleService.saveArticle(blogArticleEntity);
        return R.success("添加成功");
    }
}
