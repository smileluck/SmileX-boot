package top.zsmile.tool.wechat.gzh.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 微信授权码信息
 * Created by lesterchen on 2019/10/9.
 */
@Getter
@Setter
public class AuthCodeInfo {
    @NotEmpty
    private String appid;
    @NotEmpty
    private String redirectUrl;
    @NotEmpty
    private String responseType;
    @NotEmpty
    private String scope;
    @NotEmpty
    private String state;
    @NotEmpty
    private String selfRedirect;
}
