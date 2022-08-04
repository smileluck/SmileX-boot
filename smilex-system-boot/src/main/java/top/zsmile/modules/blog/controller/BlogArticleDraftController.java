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
import top.zsmile.modules.blog.service.BlogArticleDraftService;
import top.zsmile.modules.blog.entity.BlogArticleDraftEntity;

/**
 * 租户博客草稿箱
 */
@Api(tags = "租户博客草稿箱")
@RestController
@RequestMapping("/blog/article/draft")
public class BlogArticleDraftController {

    @Autowired
    private BlogArticleDraftService blogArticleDraftService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "租户博客草稿箱", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("blog:article:draft:list")
    @GetMapping("/list")
    public R<IPage<BlogArticleDraftEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = blogArticleDraftService.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("blog:article:draft:info")
    @GetMapping("/info/{id}")
    public R<BlogArticleDraftEntity> info(@PathVariable("id") Long id){
        BlogArticleDraftEntity info = blogArticleDraftService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "租户博客草稿箱", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("blog:article:draft:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogArticleDraftEntity blogArticleDraftEntity){
        blogArticleDraftService.updateById(blogArticleDraftEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "租户博客草稿箱", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("blog:article:draft:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        blogArticleDraftService.removePhysicsByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "租户博客草稿箱", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("blog:article:draft:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogArticleDraftEntity blogArticleDraftEntity){
        blogArticleDraftService.save(blogArticleDraftEntity);
        return R.success("添加成功");
    }
}
