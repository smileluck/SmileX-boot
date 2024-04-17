package top.zsmile.pay.constant;


import org.apache.commons.lang3.StringUtils;

public final class TradeConstant {

    /**
     * 交易类型
     */
    public static final class TradeType {
        //公众号支付
        public static final String JSAPI = "JSAPI";
        //扫码支付
        public static final String NATIVE = "NATIVE";
        //APP支付
        public static final String APP = "APP";
        //付款码支付
        public static final String MICROPAY = "MICROPAY";
        //H5支付
        public static final String MWEB = "MWEB";
        //刷脸支付
        public static final String FACEPAY = "FACEPAY";


    }

    /**
     * 交易状态
     */
    public static final class TradeState {
        //支付成功
        public static final String SUCCESS = "SUCCESS";
        //部分退款
        public static final String SECTION_REFUND = "SECTION_REFUND";
        //转入退款
        public static final String REFUND = "REFUND";
        //未支付
        public static final String NOTPAY = "NOTPAY";
        //已关闭
        public static final String CLOSED = "CLOSED";
        //已撤销（仅付款码支付会返回）
        public static final String REVOKED = "REVOKED";
        //用户支付中（仅付款码支付会返回）
        public static final String USERPAYING = "USERPAYING";
        //支付失败（仅付款码支付会返回）
        public static final String PAYERROR = "PAYERROR";
        // 订单完成
        public static final String FINISH = "FINISH";

        /**
         * 转换统一的支付状态
         *
         * @param status
         * @return
         */
        public static String convert(String type, String status) {
            String newStatus = status;
            switch (type) {
                case PayType.ALIPAY:
                    newStatus = StringUtils.replaceEach(status,
                            new String[]{"WAIT_BUYER_PAY", "TRADE_CLOSED", "TRADE_SUCCESS", "FINISH"},
                            new String[]{NOTPAY, CLOSED, SUCCESS, FINISH});
                    break;
            }
            return newStatus;
        }

    }


    /**
     * 支付类型
     */
    public static final class PayType {
        public static final String ALIPAY = "alipay";

        // TODO 临时使用
        public static final String ALIPAY2 = "alipay2";
        public static final String WXPAY = "wxpay";

        public static boolean support(String type) {
            return ALIPAY.equals(type) || WXPAY.equals(type) || ALIPAY2.equals(type);
        }
    }
}
