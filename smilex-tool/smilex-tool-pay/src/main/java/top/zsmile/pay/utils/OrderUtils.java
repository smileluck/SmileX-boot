package top.zsmile.pay.utils;

import org.apache.commons.lang3.StringUtils;
import top.zsmile.common.core.utils.uuid.SnowFlake;

public final class OrderUtils {

    private static final SnowFlake snowFlake = new SnowFlake(1, 1);

    public static String getOrderId(String prefix) {
        return StringUtils.isNotBlank(prefix) ? prefix + snowFlake.nextId() : snowFlake.nextId() + "";
    }

    public static String getOrderId() {
        return getOrderId(null);
    }
}
