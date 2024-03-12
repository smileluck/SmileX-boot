package top.zsmile.tool.wechat.gzh.service;

import top.zsmile.tool.wechat.gzh.properties.AuthCodeInfo;

public interface IWechatOfficialAccountService {

    //获取微信web认证二维码参数
    AuthCodeInfo getWxQrCodeInfo(int type);

    //微信认证回调接口,接收随机码
    String redirectAuth(String code);
}
