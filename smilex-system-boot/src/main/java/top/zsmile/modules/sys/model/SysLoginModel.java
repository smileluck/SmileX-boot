package top.zsmile.modules.sys.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录表单
 *
 * @Author scott
 * @since 2019-01-18
 */
@ApiModel(value = "登录对象", description = "登录对象")
public class SysLoginModel {
    @NotBlank(message = "请输入账号")
    @ApiModelProperty(value = "账号",required = true)
    private String username;
    @NotBlank(message = "请输入密码")
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @NotBlank(message = "请输入验证码")
    @ApiModelProperty(value = "验证码",required = true)
    private String captchaCode;
    @NotBlank(message = "请输入验证码KEY")
    @ApiModelProperty(value = "验证码key",required = true)
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
