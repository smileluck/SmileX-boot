package ${packages}.${moduleName}.controller;

import java.util.Arrays;
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
import ${packages}.${moduleName}.service.${bigHumpClass}Service;
import ${packages}.${moduleName}.entity.${bigHumpClass}Entity;

/**
 * ${tableComment}
 */
@Tag(name = "${tableComment}")
@RestController
@RequestMapping("/${reqMapping}")
public class ${bigHumpClass}Controller {

    @Autowired
    private ${bigHumpClass}Service ${smallHumpClass}Service;

    @Operation(summary="查询列表（分页）")
    @SysLog(title = "${tableComment}", operateType = CommonConstant.SYS_LOG_OPERATE_QUERY, value = "分页查询")
    @RequiresPermissions("${smallColonName}:list")
    @GetMapping("/list")
    public R<IPage<${bigHumpClass}Entity>> list(@RequestParam Map<String, Object> params) {
        IPage page = ${smallHumpClass}Service.getPageByMap(params);
        return R.success("查询成功",page);
    }

    @Operation(summary="根据Id查询信息")
    @RequiresPermissions("${smallColonName}:info")
    @GetMapping("/info/{id}")
    public R<${bigHumpClass}Entity> info(@PathVariable("id") Long id){
        ${bigHumpClass}Entity info = ${smallHumpClass}Service.getById(id);
        return R.success("查询成功",info);
    }


    @Operation(summary="根据Id更新信息")
    @SysLog(title = "${tableComment}", operateType = CommonConstant.SYS_LOG_OPERATE_UPDATE, value = "更新")
    @RequiresPermissions("${smallColonName}:update")
    @PostMapping("/update")
    public R update(@RequestBody ${bigHumpClass}Entity ${smallHumpClass}Entity){
        ${smallHumpClass}Service.updateById(${smallHumpClass}Entity);
        return R.success("修改成功");
    }

    @Operation(summary="根据id列表批量删除")
    @SysLog(title = "${tableComment}", operateType = CommonConstant.SYS_LOG_OPERATE_REMOVE, value = "删除")
    @RequiresPermissions("${smallColonName}:remove")
    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids){
        ${smallHumpClass}Service.removeByIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @Operation(summary="保存")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @SysLog(title = "${tableComment}", operateType = CommonConstant.SYS_LOG_OPERATE_SAVE, value = "新增")
    @RequiresPermissions("${smallColonName}:save")
    @PostMapping("/save")
    public R save(@RequestBody ${bigHumpClass}Entity ${smallHumpClass}Entity){
        ${smallHumpClass}Service.save(${smallHumpClass}Entity);
        return R.success("添加成功");
    }
}
