package top.zsmile.pay.service;

import top.zsmile.pay.domain.SysTransaction;

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
    SysTransaction create(String id,String handleType, String payType, Integer expireIn, String sceneInfo, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate);

    /**
     * 创建订单
     *
     * @param id
     * @param payType
     * @return
     */
    SysTransaction create(String id,String handleType, String payType, Integer expireIn, String sceneInfo, String payCurrency, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate);
}
