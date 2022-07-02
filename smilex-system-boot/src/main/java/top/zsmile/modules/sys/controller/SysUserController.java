package top.zsmile.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import top.zsmile.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.meta.IPage;
import top.zsmile.modules.sys.service.SysUserService;
import top.zsmile.modules.sys.entity.SysUserEntity;

/**
 * 系统用户管理
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage page = sysUserService.getPage(params, "id", "tenantId", "username", "realName", "enableFlag", "remark", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", page);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SysUserEntity info = sysUserService.getById(id, "id", "tenantId", "username", "realName", "enableFlag", "remark", "createTime", "createBy", "updateTime", "updateBy");
        return R.success("查询成功", info);
    }

    @PostMapping("/update")
    public R update(@RequestBody SysUserEntity sysUserEntity) {
        sysUserService.updateById(sysUserEntity);
        return R.success("修改成功");
    }


    @PostMapping("/remove")
    public R remove(@RequestBody Long[] ids) {
        sysUserService.removePhysicsBatchIds(Arrays.asList(ids));
        return R.success("删除成功");
    }

    @PostMapping("/save")
    public R save(@RequestBody SysUserEntity sysUserEntity) {
        sysUserService.saveUser(sysUserEntity);
        return R.success("添加成功");
    }
}
