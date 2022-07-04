package top.zsmile.modules.blog.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.blog.service.BlogCommentService;
import top.zsmile.modules.blog.entity.BlogCommentEntity;

/**
 * 租户博客评论
 */
@RestController
@RequestMapping("/blog/comment")
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;

    @RequiresPermissions("blog:comment:list")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = blogCommentService.getPage(params);
        return R.success("查询成功",page);
    }

    @RequiresPermissions("blog:comment:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BlogCommentEntity info = blogCommentService.getById(id);
        return R.success("查询成功",info);
    }

    @RequiresPermissions("blog:comment:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogCommentEntity blogCommentEntity){
        blogCommentService.updateById(blogCommentEntity);
        return R.success("修改成功");
    }

    @RequiresPermissions("blog:comment:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        blogCommentService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @RequiresPermissions("blog:comment:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogCommentEntity blogCommentEntity){
        blogCommentService.save(blogCommentEntity);
        return R.success("添加成功");
    }
}
