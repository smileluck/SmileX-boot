package top.zsmile.pay.service;

import top.zsmile.pay.bean.WxV3Resp;
import top.zsmile.pay.domain.SysTransaction;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信支付服务
 */
public interface IWechatPayService {


    /**
     * Naive 支付
     *
     * @param id
     * @param transaction 订单
     * @return 响应结果
     */
    PrepayResponse naivePay(String id, SysTransaction transaction);

    /**
     * 验证和放重提交
     *
     * @return
     */
    Transaction validAndRepeat(String id, HttpServletRequest request);

    /**
     * 支付通知处理
     *
     * @param id      唯一ID
     * @param request httpRequest 请求
     * @return 处理结果
     */
    WxV3Resp notifyHandle(String id, HttpServletRequest request);
}
