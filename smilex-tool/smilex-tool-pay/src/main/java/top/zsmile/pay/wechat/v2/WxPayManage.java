package top.zsmile.pay.wechat.v2;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import top.zsmile.common.utils.Asserts;
import top.zsmile.core.exception.SXException;
import top.zsmile.pay.entity.dto.OrderDTO;
import top.zsmile.pay.entity.dto.OrderRefundDTO;
import top.zsmile.pay.entity.dto.OrderRefundQueryDTO;
import top.zsmile.pay.entity.vo.OrderRefundVO;
import top.zsmile.pay.entity.vo.ReturnVO;
import top.zsmile.pay.entity.vo.OrderQueryVO;
import top.zsmile.pay.enums.PayTradeTypeEnum;
import top.zsmile.pay.factory.PayTradeTypeFactory;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;
import top.zsmile.pay.wechat.v2.handler.BaseHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信接口库
 */
@Slf4j
public class WxPayManage {

    /**
     * 统一下单
     *
     * @param config
     * @param payTradeTypeEnum
     * @param data
     * @return
     * @throws Exception
     */
    public static ReturnVO unifiedOrder(WxPayConfig config, PayTradeTypeEnum payTradeTypeEnum, Map<String, String> data) throws Exception {
        log.debug("正在进行操作 ==> {}，参数值 ==> {}", payTradeTypeEnum.getValue(), data);
        BaseHandler baseHandler = PayTradeTypeFactory.get(payTradeTypeEnum.getValue());
        ReturnVO returnVO = baseHandler.unifiedOrder(config, data);
        return returnVO;
    }

    /**
     * 查询订单
     *
     * @param config        配置
     * @param orderQueryDTO 查询订单实体
     * @return
     * @throws Exception
     */
    public static ReturnVO orderQuery(WxPayConfig config, OrderDTO orderQueryDTO) throws Exception {
        log.debug("正在进行操作 ==> 查询订单，参数值 ==> {}", orderQueryDTO);
        Map map = checkOrderDTO(orderQueryDTO);
        WxPayCore wxPay = WxPayCore.of(config);
        Map resMap = wxPay.orderQuery(map);
        ReturnVO returnVO = WxPayUtil.mapToResult(resMap);
        if (WxPayUtil.checkResultState(returnVO)) {
            returnVO.setOrderQueryVO(OrderQueryVO.of(map));
            return returnVO;
        } else {
            throw new SXException("查询订单异常，异常原因：" + returnVO.getReturnMsg());
        }
    }


    /**
     * 撤销订单
     *
     * @param config        配置
     * @param orderQueryDTO 查询订单实体
     * @return
     * @throws Exception
     */
    public static ReturnVO reverse(WxPayConfig config, OrderDTO orderQueryDTO) throws Exception {
        log.debug("正在进行操作 ==> 撤销订单，参数值 ==> {}", orderQueryDTO);
        Map map = checkOrderDTO(orderQueryDTO);
        WxPayCore wxPay = WxPayCore.of(config);
        Map resMap = wxPay.reverse(map);
        ReturnVO returnVO = WxPayUtil.mapToResult(resMap);
        if (WxPayUtil.checkResultState(returnVO)) {
            returnVO.setOrderQueryVO(OrderQueryVO.of(map));
            return returnVO;
        } else {
            throw new SXException("撤销订单异常，异常原因：" + returnVO.getReturnMsg());
        }
    }


    /**
     * 退款订单
     *
     * @param config   配置
     * @param orderDTO 退款订单实体
     * @return
     * @throws Exception
     */
    public static ReturnVO refund(WxPayConfig config, OrderRefundDTO orderDTO) throws Exception {
        log.debug("正在进行操作 ==> 退款订单，参数值 ==> {}", orderDTO);
        Map map = checkOrderDTO(orderDTO);

        Asserts.isBlank(orderDTO.getOutRefundNo(), "商户退款单号不能为空");
        if (orderDTO.getTotalFee() == null || orderDTO.getTotalFee() <= 0) {
            throw new IllegalArgumentException("订单金额需大于0");
        }
        if (orderDTO.getRefundFee() == null || orderDTO.getRefundFee() <= 0) {
            throw new IllegalArgumentException("退款金额需大于0");
        }
        map.put("out_refund_no", orderDTO.getOutRefundNo());
        map.put("total_fee", orderDTO.getTotalFee());
        map.put("refund_fee", orderDTO.getRefundFee());
        if (StringUtils.isNotBlank(orderDTO.getRefundFeeType()))
            map.put("refund_fee_type", orderDTO.getRefundFeeType());

        if (StringUtils.isNotBlank(orderDTO.getRefundDesc()))
            map.put("refund_desc", orderDTO.getRefundDesc());

        if (orderDTO.getRefundAccount() != null)
            map.put("refund_account", orderDTO.getRefundAccount().getValue());

        if (StringUtils.isNotBlank(orderDTO.getNotifyUrl()))
            map.put("notify_url", orderDTO.getNotifyUrl());

        WxPayCore wxPay = WxPayCore.of(config);
        Map resMap = wxPay.reverse(map);
        ReturnVO returnVO = WxPayUtil.mapToResult(resMap);
        if (WxPayUtil.checkResultState(returnVO)) {
            returnVO.setOrderRefundVO(OrderRefundVO.of(map));
            return returnVO;
        } else {
            throw new SXException("撤销订单异常，异常原因：" + returnVO.getReturnMsg());
        }
    }


    /**
     * 退款订单查询
     *
     * @param config        配置
     * @param orderQueryDTO 查询订单实体
     * @return
     * @throws Exception
     */
    public static ReturnVO refundQuery(WxPayConfig config, OrderRefundQueryDTO orderQueryDTO) throws Exception {
        log.debug("正在进行操作 ==> 退款订单查询，参数值 ==> {}", orderQueryDTO);

        Map<String, String> map = null;
        if (StringUtils.isNotBlank(orderQueryDTO.getRefundId())) {
            map = new HashMap<>();
            map.put("refund_id", orderQueryDTO.getRefundId());
        } else if (StringUtils.isNotBlank(orderQueryDTO.getOutRefundNo())) {
            map = new HashMap<>();
            map.put("out_refund_no", orderQueryDTO.getOutRefundNo());
        } else if (StringUtils.isNotBlank(orderQueryDTO.getTransactionId())) {
            map = new HashMap<>();
            map.put("transaction_id", orderQueryDTO.getTransactionId());
        } else if (StringUtils.isNotBlank(orderQueryDTO.getOutTradeNo())) {
            map = new HashMap<>();
            map.put("out_trade_no", orderQueryDTO.getOutTradeNo());
        } else {
            throw new IllegalArgumentException("transaction_id/out_trade_no/out_refund_no/refund_id cannot be empty at the same time");
        }

        if (orderQueryDTO.getOffset() != null && orderQueryDTO.getOffset() >= 0)
            map.put("offset", orderQueryDTO.getOffset().intValue() + "");

        WxPayCore wxPay = WxPayCore.of(config);
        Map resMap = wxPay.refundQuery(map);
        ReturnVO returnVO = WxPayUtil.mapToResult(resMap);
        if (WxPayUtil.checkResultState(returnVO)) {
            returnVO.setOrderQueryVO(OrderQueryVO.of(map));
            return returnVO;
        } else {
            throw new SXException("退款订单查询异常，异常原因：" + returnVO.getReturnMsg());
        }
    }


    private static Map<String, String> checkOrderDTO(OrderDTO orderQueryDTO) {
        if (StringUtils.isNotBlank(orderQueryDTO.getTransactionId())) {
            Map<String, String> map = new HashMap<>();
            map.put("transaction_id", orderQueryDTO.getTransactionId());
            return map;
        } else if (StringUtils.isNotBlank(orderQueryDTO.getOutTradeNo())) {
            Map<String, String> map = new HashMap<>();
            map.put("out_trade_no", orderQueryDTO.getOutTradeNo());
            return map;
        } else {
            throw new IllegalArgumentException("Both transaction_id and out_trade_no cannot be empty at the same time");
        }
    }
}
