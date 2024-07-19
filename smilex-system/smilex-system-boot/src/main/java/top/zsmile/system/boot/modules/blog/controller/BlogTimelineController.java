package top.zsmile.system.boot.modules.blog.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import top.zsmile.common.core.api.R;
import top.zsmile.common.log.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.system.boot.modules.blog.service.BlogTimelineService;
import top.zsmile.system.boot.modules.blog.entity.BlogTimelineEntity;

/**
 * 博客时间线
 */
@Tag(name  = "博客时间线")
@RestController
@RequestMapping("/blog/timeline")
public class BlogTimelineController {

    @Autowired
    private BlogTimelineService blogTimelineService;

    @Operation(summary="查询列表（分页）")
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("blog:timeline:list")
    @GetMapping("/list")
    public R<IPage<BlogTimelineEntity>> list(@RequestParam Map<String, Object> params) {
        IPage page = blogTimelineService.getPageByMap(params);
        return R.success("查询成功", page);
    }

    @Operation(summary="根据Id查询信息")
    @RequiresPermissions("blog:timeline:info")
    @GetMapping("/info/{id}")
    public R<BlogTimelineEntity> info(@PathVariable("id") Long id) {
        BlogTimelineEntity info = blogTimelineService.getById(id);
        return R.success("查询成功", info);
    }


    @Operation(summary="根据Id更新信息")
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("blog:timeline:update")
    @PostMapping("/update")
    public R update(@RequestBody BlogTimelineEntity blogTimelineEntity) {
        blogTimelineService.updateById(blogTimelineEntity);
        return R.success("修改成功");
    }

    @Operation(summary="根据id列表批量删除")
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("blog:timeline:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        blogTimelineService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @Operation(summary="保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "博客时间线", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("blog:timeline:save")
    @PostMapping("/save")
    public R save(@RequestBody BlogTimelineEntity blogTimelineEntity) {
        String year = blogTimelineEntity.getYear();

        BlogTimelineEntity info = blogTimelineService.getObjByMap(Collections.singletonMap("year", year));
        if (info != null) {
            return R.fail("当前年份已添加");
        }
        blogTimelineService.save(blogTimelineEntity);
        return R.success("添加成功");
    }
}
