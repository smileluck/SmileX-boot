package top.zsmile.pay.service;

import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.vo.MiniPrepayVO;
import top.zsmile.pay.vo.NaivePrepayVO;

import java.math.BigDecimal;

/**
 * 订单服务
 */
public interface ITransactionService {

    /**
     * 创建订单
     *
     * @param id
     * @param expireIn
     * @return
     */
    SysTransaction create(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate);

    /**
     * 创建订单
     *
     * @param id
     * @param payType
     * @return
     */
    SysTransaction create(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String payCurrency, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate);

    /**
     * 创建订单并保存
     *
     * @param id
     * @param expireIn
     * @return
     */
    SysTransaction createAndSave(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate);

    /**
     * 创建订单并保存
     *
     * @param id
     * @param payType
     * @return
     */
    SysTransaction createAndSave(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String payCurrency, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate);

    /**
     * 保存订单
     *
     * @param sysTransaction
     */
    void save(SysTransaction sysTransaction);

    /**
     * 扫码支付
     *
     * @param id
     * @param transaction 订单
     * @return 响应结果
     */
    NaivePrepayVO scanPrepay(String id, SysTransaction transaction);


    /**
     * 微信小程序支付
     *
     * @param id
     * @param transaction 订单
     * @return 响应结果
     */
    MiniPrepayVO miniPrepay(String id, SysTransaction transaction);

}
