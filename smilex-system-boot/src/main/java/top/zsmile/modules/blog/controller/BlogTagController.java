package top.zsmile.modules.blog.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.blog.service.BlogTagService;
import top.zsmile.modules.blog.entity.BlogTagEntity;

/**
 * 租户博客标签
 */
@RestController
@RequestMapping("/blog/tag")
public class BlogTagController {

    @Autowired
    private BlogTagService blogTagService;

    @RequiresPermissions("blog:tag:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = blogTagService.getPage(params);
        return R.success("查询成功",page);
    }

    @RequiresPermissions("blog:tag:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BlogTagEntity info = blogTagService.getById(id);
        return R.success("查询成功",info);
    }

    @RequiresPermissions("blog:tag:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogTagEntity blogTagEntity){
        blogTagService.updateById(blogTagEntity);
        return R.success("修改成功");
    }

    @RequiresPermissions("blog:tag:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        blogTagService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @RequiresPermissions("blog:tag:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogTagEntity blogTagEntity){
        blogTagService.save(blogTagEntity);
        return R.success("添加成功");
    }
}
