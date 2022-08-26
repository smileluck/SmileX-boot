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
import top.zsmile.modules.blog.service.BlogTimelineService;
import top.zsmile.modules.blog.entity.BlogTimelineEntity;

/**
 * 博客时间线
 */
@Api(tags = "博客时间线")
@RestController
@RequestMapping("/blog/timeline")
public class BlogTimelineController {

    @Autowired
    private BlogTimelineService blogTimelineService;

    @ApiOperation("查询列表（分页）")
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("blog:timeline:list")
    @GetMapping("/list")
    public R<IPage<BlogTimelineEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = blogTimelineService.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @ApiOperation("根据Id查询信息")
    @RequiresPermissions("blog:timeline:info")
    @GetMapping("/info/{id}")
    public R<BlogTimelineEntity> info(@PathVariable("id") Long id){
        BlogTimelineEntity info = blogTimelineService.getById(id);
        return R.success("查询成功",info);
    }


    @ApiOperation("根据Id更新信息")
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("blog:timeline:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogTimelineEntity blogTimelineEntity){
        blogTimelineService.updateById(blogTimelineEntity);
        return R.success("修改成功");
    }

    @ApiOperation("根据id列表批量删除")
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("blog:timeline:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        blogTimelineService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @ApiOperation("保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("blog:timeline:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogTimelineEntity blogTimelineEntity){
        blogTimelineService.save(blogTimelineEntity);
        return R.success("添加成功");
    }
}
