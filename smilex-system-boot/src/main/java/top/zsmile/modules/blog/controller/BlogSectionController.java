package top.zsmile.modules.blog.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.blog.service.BlogSectionService;
import top.zsmile.modules.blog.entity.BlogSectionEntity;

/**
 * 租户博客栏目
 */
@RestController
@RequestMapping("/blog/section")
public class BlogSectionController {

    @Autowired
    private BlogSectionService blogSectionService;

    @RequiresPermissions("blog:section:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = blogSectionService.getPage(params);
        return R.success("查询成功",page);
    }

    @RequiresPermissions("blog:section:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BlogSectionEntity info = blogSectionService.getById(id);
        return R.success("查询成功",info);
    }

    @RequiresPermissions("blog:section:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogSectionEntity blogSectionEntity){
        blogSectionService.updateById(blogSectionEntity);
        return R.success("修改成功");
    }

    @RequiresPermissions("blog:section:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        blogSectionService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @RequiresPermissions("blog:section:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogSectionEntity blogSectionEntity){
        blogSectionService.save(blogSectionEntity);
        return R.success("添加成功");
    }
}
