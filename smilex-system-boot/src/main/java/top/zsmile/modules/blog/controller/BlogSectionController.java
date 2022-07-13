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
import top.zsmile.modules.blog.service.BlogSectionService;
import top.zsmile.modules.blog.entity.BlogSectionEntity;

/**
 * 租户博客栏目
 */
@Api(tags = "租户博客栏目")
@RestController
@RequestMapping("/blog/section")
public class BlogSectionController {

    @Autowired
    private BlogSectionService blogSectionService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "租户博客栏目", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("blog:section:list")
    @GetMapping("/list")
    public R<IPage<BlogSectionEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = blogSectionService.getPage(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("blog:section:info")
    @GetMapping("/info/{id}")
    public R<BlogSectionEntity> info(@PathVariable("id") Long id){
        BlogSectionEntity info = blogSectionService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "租户博客栏目", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("blog:section:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogSectionEntity blogSectionEntity){
        blogSectionService.updateById(blogSectionEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "租户博客栏目", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("blog:section:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        blogSectionService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "租户博客栏目", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("blog:section:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogSectionEntity blogSectionEntity){
        blogSectionService.save(blogSectionEntity);
        return R.success("添加成功");
    }
}
