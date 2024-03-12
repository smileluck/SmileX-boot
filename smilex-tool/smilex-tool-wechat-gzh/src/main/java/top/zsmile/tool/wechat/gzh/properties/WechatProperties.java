package top.zsmile.tool.wechat.gzh.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class WechatProperties {


    private String appId;

    private String appSecret;

    private String redirectUrl;
}
