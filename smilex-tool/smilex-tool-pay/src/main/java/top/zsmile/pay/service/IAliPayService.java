package top.zsmile.pay.service;

import top.zsmile.pay.domain.SysTransaction;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付包支付
 */
public interface IAliPayService {


    /**
     * 扫码支付
     *
     * @param id          id
     * @param transaction 订单
     * @return 响应结果
     */
    String scanPrePay(String id, SysTransaction transaction);

    /**
     * 电脑网站支付
     *
     * @param id          id
     * @param transaction 订单
     * @return 响应结果
     */
    String pcWebPay(String id, SysTransaction transaction);

    /**
     * 通知应答
     *
     * @param id      id
     * @param request 请求
     * @return 响应结果
     */
    String notifyHandle(String id, HttpServletRequest request);
}
