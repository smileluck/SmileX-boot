package top.zsmile.pay.service;

import com.wechat.pay.java.service.refund.model.Refund;
import top.zsmile.pay.bean.WxV3Resp;
import top.zsmile.pay.domain.SysTransaction;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import top.zsmile.pay.domain.SysTransactionRefund;
import top.zsmile.pay.vo.MiniPrepayVO;

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
     * 小程序支付
     *
     * @param id
     * @param transaction 订单
     * @return 响应结果
     */
    com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse miniPay(String id, SysTransaction transaction);


    /**
     * 小程序支付
     *
     * @param id
     * @param transaction 订单
     * @return 响应结果
     */
    MiniPrepayVO miniPayPack(String id, SysTransaction transaction);

    /**
     * 验证和放重提交
     *
     * @return
     */
    <T> T validAndRepeat(String id, HttpServletRequest request, Class<T> notifyClass);

    /**
     * 支付通知处理
     *
     * @param id      唯一ID
     * @param request httpRequest 请求
     * @return 处理结果
     */
    WxV3Resp notifyHandle(String id, HttpServletRequest request);

    /**
     * 退款通知处理
     *
     * @param id      唯一ID
     * @param request httpRequest 请求
     * @return 处理结果
     */
    WxV3Resp notifyRefundHandle(String id, HttpServletRequest request);

    /**
     * 微信退款
     *
     * @param id
     * @param sysTransaction       订单
     * @param sysTransactionRefund 退款订单
     * @return 响应结果
     */
    Refund refund(String id, SysTransaction sysTransaction, SysTransactionRefund sysTransactionRefund);
}
