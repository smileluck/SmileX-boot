package top.zsmile.tool.pay.wechat.v2;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.zsmile.common.core.utils.Asserts;
import top.zsmile.core.exception.SXException;
import top.zsmile.tool.pay.entity.dto.OrderDTO;
import top.zsmile.tool.pay.entity.dto.OrderRefundDTO;
import top.zsmile.tool.pay.entity.dto.OrderRefundQueryDTO;
import top.zsmile.tool.pay.entity.vo.OrderRefundVO;
import top.zsmile.tool.pay.entity.vo.ReturnVO;
import top.zsmile.tool.pay.entity.vo.OrderQueryVO;
import top.zsmile.tool.pay.enums.PayTradeTypeEnum;
import top.zsmile.tool.pay.factory.PayTradeTypeFactory;
import top.zsmile.tool.pay.wechat.v2.config.WxPayConfig;
import top.zsmile.tool.pay.wechat.v2.handler.BaseHandler;

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
        log.debug("正在进行操作 ==> 统一下单-{}，参数值 ==> {}", payTradeTypeEnum.getValue(), data);
        BaseHandler baseHandler = PayTradeTypeFactory.get(payTradeTypeEnum.getValue());
        ReturnVO returnVO = baseHandler.unifiedOrder(config, data);
        log.debug("正在进行操作 ==> 统一下单-{}，响应结果 ==> {}", payTradeTypeEnum.getValue(), returnVO);
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
        log.debug("正在进行操作 ==> 查询订单，响应结果 ==> {}", resMap);
        ReturnVO returnVO = WxPayUtil.mapToResult(config, resMap);
        if (WxPayUtil.checkResponseState(returnVO)) {
            returnVO.setOrderQueryVO(OrderQueryVO.of(map));
            return returnVO;
        } else {
            throw new SXException("查询订单异常，异常原因：" + returnVO.getReturnMsg());
        }
    }

    /**
     * 关闭订单
     *
     * @param config     配置
     * @param outTradeNo 商户订单号
     * @return
     * @throws Exception
     */
    public static ReturnVO closeOrder(WxPayConfig config, String outTradeNo) throws Exception {
        log.debug("正在进行操作 ==> 关闭订单，参数值 ==> {}", outTradeNo);
        Asserts.isNotBlank(outTradeNo, "out_trade_no cannot be empty");
        Map<String, String> map = new HashMap<>();
        map.put("out_trade_no", outTradeNo);
        WxPayCore wxPay = WxPayCore.of(config);
        Map resMap = wxPay.closeOrder(map);
        log.debug("正在进行操作 ==> 关闭订单，响应结果 ==> {}", resMap);
        ReturnVO returnVO = WxPayUtil.mapToResult(config, resMap);
        if (WxPayUtil.checkResponseState(returnVO)) {
            return returnVO;
        } else {
            throw new SXException("关闭订单，异常原因：" + returnVO.getReturnMsg());
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
        log.debug("正在进行操作 ==> 撤销订单，响应结果 ==> {}", resMap);
        ReturnVO returnVO = WxPayUtil.mapToResult(config, resMap);
        if (WxPayUtil.checkResponseState(returnVO)) {
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
        ReturnVO returnVO = WxPayUtil.mapToResult(config, resMap);
        log.debug("正在进行操作 ==> 退款订单，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(returnVO)) {
            returnVO.setOrderRefundVO(OrderRefundVO.of(map));
            return returnVO;
        } else {
            throw new SXException("退款订单异常，异常原因：" + returnVO.getReturnMsg());
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
    public static Map<String, String> refundQuery(WxPayConfig config, OrderRefundQueryDTO orderQueryDTO) throws Exception {
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
        Map<String, String> resMap = wxPay.refundQuery(map);
        log.debug("正在进行操作 ==> 退款订单查询，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("退款订单查询异常，异常原因：" + resMap.get("return_msg"));
        }
    }


    /**
     * 下载交易账单
     *
     * @param config 配置
     * @param map    下载交易账单参数对象
     * @return
     * @throws Exception
     */
    public static Map<String, String> downloadBill(WxPayConfig config, Map<String, String> map) throws Exception {
        log.debug("正在进行操作 ==> 下载交易账单，参数值 ==> {}", map);

        WxPayCore wxPay = WxPayCore.of(config);
        Map<String, String> resMap = wxPay.downloadBill(map);
        log.debug("正在进行操作 ==> 下载交易账单，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("下载交易账单异常，异常原因：" + resMap.get("return_msg"));
        }
    }

    /**
     * 下载资金账单
     *
     * @param config 配置
     * @param map    下载资金账单参数对象
     * @return
     * @throws Exception
     */
    public static Map<String, String> downloadFundFlow(WxPayConfig config, Map<String, String> map) throws Exception {
        log.debug("正在进行操作 ==> 下载资金账单，参数值 ==> {}", map);

        WxPayCore wxPay = WxPayCore.of(config);
        Map<String, String> resMap = wxPay.downloadFundFlow(map);
        log.debug("正在进行操作 ==> 下载资金账单，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("下载资金账单异常，异常原因：" + resMap.get("return_msg"));
        }
    }


    /**
     * 交易保障
     *
     * @param config 配置
     * @param map    交易保障参数对象
     * @return
     * @throws Exception
     */
    public static Map<String, String> report(WxPayConfig config, Map<String, String> map) throws Exception {
        log.debug("正在进行操作 ==> 交易保障，参数值 ==> {}", map);
        WxPayCore wxPay = WxPayCore.of(config);
        Map<String, String> resMap = wxPay.report(map);
        log.debug("正在进行操作 ==> 交易保障，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("交易保障异常，异常原因：" + resMap.get("return_msg"));
        }
    }

    /**
     * 转换短链接
     *
     * @param config
     * @param data
     * @return
     * @throws Exception
     */
    public static Map<String, String> shortUrl(WxPayConfig config, Map<String, String> data) throws Exception {
        log.debug("正在进行操作 ==> 转换短链接，参数值 ==> {}", data);
        WxPayCore wxPay = WxPayCore.of(config);
        Map<String, String> resMap = wxPay.shortUrl(data);
        log.debug("正在进行操作 ==> 转换短链接，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("转换短链接，异常原因：" + resMap.get("return_msg"));
        }
    }

    /**
     * 付款码查询openid
     *
     * @param config
     * @param data
     * @return
     * @throws Exception
     */
    public static Map<String, String> authCodeToOpenid(WxPayConfig config, Map<String, String> data) throws Exception {
        log.debug("正在进行操作 ==> 付款码查询openid，参数值 ==> {}", data);
        WxPayCore wxPay = WxPayCore.of(config);
        Map<String, String> resMap = wxPay.authCodeToOpenid(data);
        log.debug("正在进行操作 ==> 付款码查询openid，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("付款码查询openid，异常原因：" + resMap.get("return_msg"));
        }
    }

    /**
     * 企业付款到零钱
     *
     * @param config
     * @param data
     * @return
     * @throws Exception
     */
    public static Map<String, String> transfersWallet(WxPayConfig config, Map<String, String> data) throws Exception {
        log.debug("正在进行操作 ==> 企业付款到零钱，参数值 ==> {}", data);
        WxPayCore wxPay = WxPayCore.of(config);
        Map<String, String> resMap = wxPay.transfersWallet(data);
        log.debug("正在进行操作 ==> 企业付款到零钱，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("企业付款到零钱，异常原因：" + resMap.get("return_msg"));
        }
    }


    /**
     * @param config
     * @param data
     * @return
     * @throws Exception
     */
    public static Map<String, String> transfersWalletInfo(WxPayConfig config, Map<String, String> data) throws Exception {
        log.debug("正在进行操作 ==> 企业付款到零钱查询，参数值 ==> {}", data);
        WxPayCore wxPay = WxPayCore.of(config);
        Map<String, String> resMap = wxPay.transfersWalletInfo(data);
        log.debug("正在进行操作 ==> 企业付款到零钱查询，响应结果 ==> {}", resMap);
        if (WxPayUtil.checkResponseState(resMap)) {
            return resMap;
        } else {
            throw new SXException("企业付款到零钱查询，异常原因：" + resMap.get("return_msg"));
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
