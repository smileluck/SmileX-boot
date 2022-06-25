package top.zsmile.modules.sys.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.cache.utils.RedisUtils;
import top.zsmile.common.constant.CommonConstant;
import top.zsmile.common.utils.JwtUtils;
import top.zsmile.common.utils.ValidatorUtils;
import top.zsmile.core.api.R;
import top.zsmile.core.api.ResultCode;
import top.zsmile.modules.sys.model.SysLoginModel;
import top.zsmile.modules.sys.service.SysUserService;
import top.zsmile.modules.sys.utils.CaptchaImgUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统登录相关接口
 */
@RestController
@RequestMapping("/sys/login")
public class SysLoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 登录提交
     *
     * @return
     */
    @PostMapping("/submit")
    public R submit(@RequestBody SysLoginModel sysLoginModel) {
        ValidatorUtils.validateEntity(sysLoginModel);

        String captchaKey = sysLoginModel.getCaptchaKey();
        Object o = redisUtils.get(CommonConstant.SYS_LOGIN_CAPTCHA + captchaKey);
        if (o == null) {
            return R.fail("验证码已过期");
        }
        if (!o.toString().equalsIgnoreCase(sysLoginModel.getCaptchaCode())) {
            return R.fail("验证码输入错误");
        }

        String token = sysUserService.login(sysLoginModel);
        redisUtils.del(CommonConstant.SYS_LOGIN_CAPTCHA + captchaKey);
        return R.success("登录成功", token);
    }

    /**
     * 获取验证码
     *
     * @param captchaKey
     */
    @GetMapping("/captcha/{captchaKey}")
    public R captcha(@PathVariable String captchaKey) {
        String random = RandomStringUtils.randomAlphabetic(4);
        try {
            String generate = CaptchaImgUtils.generate(random);
            redisUtils.set(CommonConstant.SYS_LOGIN_CAPTCHA + captchaKey, random);
            R<Object> success = R.success();
            success.setData(generate);
            return success;
        } catch (IOException e) {
            return R.fail("获取失败");
        }
    }

}
