package top.zsmile.pay.utils;


import top.zsmile.common.utils.SnowFlake;

/**
 * 订单号工具
 * 使用雪花算法生成
 */
public class OrderUtils {
    private static SnowFlake snowFlake = new SnowFlake(1, 1);

    public static long getOrderNo() {
        return snowFlake.nextId();
    }
}
