package top.zsmile.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.common.utils.ValidatorUtils;
import top.zsmile.core.api.R;
import top.zsmile.modules.sys.model.SysLoginModel;
import top.zsmile.modules.sys.service.SysUserService;

/**
 * 系统登录相关接口
 */
@RestController
@RequestMapping("/sys/login")
public class SysLoginController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录提交
     *
     * @return
     */
    @PostMapping("/submit")
    public R submit(@RequestBody SysLoginModel sysLoginModel) {
        ValidatorUtils.validateEntity(sysLoginModel);
        String token = sysUserService.login(sysLoginModel);
        return R.success("登录成功", token);
    }

    @GetMapping("/captcha")
    public void captcha() {

    }

}
