package top.zsmile.pay.utils;

import top.zsmile.common.core.utils.uuid.IdUtils;
import top.zsmile.pay.bean.WxV3Storage;
import top.zsmile.pay.vo.MiniPrepayVO;
import com.wechat.pay.java.core.util.PemUtil;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

/**
 * 微信支付工具类
 */
public class WechatPayUtils {


    /**
     * 包装并签名返回给JSAPI调用的实体
     *
     * @param storage       微信支付存储信息
     * @param transactionId 订单id
     * @param prepayId      预支付id
     * @param expireTime    过期时间
     * @return 支付信息包装对象
     */
    public static MiniPrepayVO packageJsApiResult(WxV3Storage storage, Long transactionId, String prepayId, LocalDateTime expireTime) {
        MiniPrepayVO miniPrepayVO = MiniPrepayVO.of(transactionId, prepayId, expireTime);
        miniPrepayVO.setPrepayId("prepay_id=" + prepayId);
        miniPrepayVO.setAppid(storage.getAppid());
        miniPrepayVO.setTimeStamp(LocalDateTime.now().getNano() / 1000 + "");
        miniPrepayVO.setNonceStr(IdUtils.fastSimpleUUID());
        miniPrepayVO.setSignType("RSA");
        miniPrepayVO.setPaySign(signBySHA256withRSA(storage.getKeyPemPath(), miniPrepayVO));
        return miniPrepayVO;
    }

    /**
     * 签名信息
     *
     * @param path         私钥证书
     * @param miniPrepayVO 签名对象
     * @return 签名字符串
     */
    public static String signBySHA256withRSA(String path, MiniPrepayVO miniPrepayVO) {
        String preSign = miniPrepayVO.getAppid() + "\n"
                + miniPrepayVO.getTimeStamp() + "\n"
                + miniPrepayVO.getNonceStr() + "\n"
                + miniPrepayVO.getPrepayId() + "\n";
        return signBySHA256withRSA(path, preSign);
    }

    /**
     * 签名信息
     *
     * @param path    私钥证书
     * @param preSign 预签名
     * @return 签名字符串
     */
    public static String signBySHA256withRSA(String path, String preSign) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            // 获取商户私钥并进行签名
            PrivateKey privateKey = PemUtil.loadPrivateKeyFromPath(path);
            sign.initSign(privateKey);
            sign.update(preSign.getBytes(StandardCharsets.UTF_8));
            // 得到签名
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

}
