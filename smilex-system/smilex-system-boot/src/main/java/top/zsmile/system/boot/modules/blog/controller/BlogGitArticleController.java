package top.zsmile.system.boot.modules.blog.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import top.zsmile.common.core.utils.ValidatorUtils;
import top.zsmile.common.core.validator.group.Add;
import top.zsmile.common.core.api.R;
import top.zsmile.common.log.annotation.SysLog;
import top.zsmile.common.core.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.system.boot.enums.VisitTypeEnum;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.system.boot.modules.blog.entity.BlogArticleEntity;
import top.zsmile.system.boot.modules.blog.entity.BlogTagEntity;
import top.zsmile.system.boot.modules.blog.entity.dto.BlogGitArticlePublish;
import top.zsmile.system.boot.modules.blog.service.BlogArticleService;
import top.zsmile.system.boot.modules.blog.service.BlogGitArticleService;
import top.zsmile.system.boot.modules.blog.entity.BlogGitArticleEntity;
import top.zsmile.system.boot.modules.blog.service.BlogTagService;

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
    public R publish(@RequestBody BlogGitArticlePublish blogGitArticlePublish) {
        BlogGitArticleEntity info = blogGitArticleService.getById(blogGitArticlePublish.getId());
        if (info == null) {
            return R.fail("该文章不存在");
        }
        // 如果已经发布过则更新文章内容
        if (info.getBlogArticleId() != null) {
            BlogArticleEntity articleEntity = new BlogArticleEntity();
            articleEntity.setId(info.getBlogArticleId());
            articleEntity.setArticleContent(info.getContentText());
            blogArticleService.updateById(articleEntity);
            BlogGitArticleEntity blogGitArticleEntity = new BlogGitArticleEntity();
            blogGitArticleEntity.setId(info.getId());
            blogGitArticleEntity.setPublishTime(LocalDateTime.now());
            blogGitArticleService.updateById(blogGitArticleEntity);
        } else {
            ValidatorUtils.validateEntity(blogGitArticlePublish, Add.class);
            if (blogGitArticlePublish.getVisitType() == VisitTypeEnum.ISOLATE.getValue()) {
                if (StringUtils.isBlank(blogGitArticlePublish.getPassword())) {
                    return R.fail("请输入独立密码");
                }
            }
            BlogArticleEntity articleEntity = new BlogArticleEntity();
            BeanUtils.copyProperties(blogGitArticlePublish, articleEntity);
            articleEntity.setId(null);
            String[] tagIds = articleEntity.getTagIds().split(",");
            List<BlogTagEntity> tagEntities = blogTagService.listByIds(Arrays.asList(tagIds), "tagName");
            String tagNames = tagEntities.stream().map(BlogTagEntity::getTagName).collect(Collectors.joining(","));
            articleEntity.setTagNames(tagNames);
            blogArticleService.saveArticle(articleEntity);

            BlogGitArticleEntity blogGitArticleEntity = new BlogGitArticleEntity();
            blogGitArticleEntity.setId(info.getId());
            blogGitArticleEntity.setBlogArticleId(articleEntity.getId());
            blogGitArticleEntity.setPublishTime(LocalDateTime.now());
            blogGitArticleEntity.setPublishFlag(1);
            blogGitArticleService.updateById(blogGitArticleEntity);
        }
        return R.success("添加成功");
    }

}
