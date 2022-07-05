package top.zsmile.modules.blog.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.blog.service.BlogArticleService;
import top.zsmile.modules.blog.entity.BlogArticleEntity;

/**
 * 租户博客文章
 */
@RestController
@RequestMapping("/blog/article")
public class BlogArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    @RequiresPermissions("blog:article:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = blogArticleService.getPage(params, "id", "tenantId", "sectionId", "tagIds", "articleTitle", "articleContent", "grammarType", "visitType", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", page);
    }

    @RequiresPermissions("blog:article:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        BlogArticleEntity info = blogArticleService.getById(id, "id", "tenantId", "sectionId", "tagIds", "articleTitle", "articleContent", "grammarType", "visitType", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", info);
    }

    @RequiresPermissions("blog:article:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogArticleEntity blogArticleEntity) {
        blogArticleService.updateById(blogArticleEntity);
        return R.success("修改成功");
    }

    @RequiresPermissions("blog:article:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        blogArticleService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @RequiresPermissions("blog:article:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogArticleEntity blogArticleEntity) {
        blogArticleService.save(blogArticleEntity);
        return R.success("添加成功");
    }
}
