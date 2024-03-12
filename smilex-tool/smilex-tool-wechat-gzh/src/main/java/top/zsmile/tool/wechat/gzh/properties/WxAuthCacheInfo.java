package top.zsmile.tool.wechat.gzh.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 微信授权缓存信息
 * Created by lesterchen on 2019/10/9.
 */
@Getter
@Setter
public class WxAuthCacheInfo implements Serializable {
    // 内部使用
    // "扫码状态，1未扫码，2已授权成功，3失败，4内部失败，5授权信息")
    private Integer status = 1;
    // "内部状态，仅在授权状态2时使用，1成功，2授权失败")
    private Integer inStatus = 1;
    // "内部状态消息，仅在授权状态2时使用")
    private String inMsg;
    // "type")
    private Integer type;    //授权类型,WxAuthCodeType文件
    // "IP")
    private String ip;
    // "用户ID")
    private Long userId;
    // "操作ID")
    private Long operaId;
    // "消息")
    private String msg;

    // 微信获取
    // "nickname")
    private String nickname;
    // "sex")
    private Integer sex;
    // "headImgUrl")
    private String headImgUrl;
    // "openid")
    private String openid;
    // "unionid")
    private String unionid;


    public static WxAuthCacheInfo of(Long userId) {
        WxAuthCacheInfo wxAuthCacheInfo = new WxAuthCacheInfo();
        wxAuthCacheInfo.setUserId(userId);
        return wxAuthCacheInfo;
    }


    public static WxAuthCacheInfo of(Long userId, Long operaId) {
        WxAuthCacheInfo wxAuthCacheInfo = new WxAuthCacheInfo();
        wxAuthCacheInfo.setUserId(userId);
        wxAuthCacheInfo.setOperaId(operaId);
        return wxAuthCacheInfo;
    }
}
