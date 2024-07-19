package top.zsmile.modules.sys.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 * @Author scott
 * @since 2019-01-18
 */
@Schema(description = "登录对象")
public class SysLoginModel {
    @NotBlank(message = "请输入账号")
    @Schema(description = "账号",required = true)
    private String username;
    @NotBlank(message = "请输入密码")
    @Schema(description = "密码",required = true)
    private String password;
    @NotBlank(message = "请输入验证码")
    @Schema(description = "验证码",required = true)
    private String captchaCode;
    @NotBlank(message = "请输入验证码KEY")
    @Schema(description = "验证码key",required = true)
    private String captchaKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }
}
