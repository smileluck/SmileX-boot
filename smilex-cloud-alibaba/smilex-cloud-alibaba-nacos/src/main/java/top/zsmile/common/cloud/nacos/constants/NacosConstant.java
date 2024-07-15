package top.zsmile.common.cloud.nacos.constants;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/13/19:01
 * @ClassName: NacosConstant
 * @Description: Nacos 常量类
 */
public interface NacosConstant {
    public static String REFRESH_DB_PREFIX = "smilex.nacos.listener.refresh-db";
    public static String REFRESH_DB_ENABLED = "enabled";
    public static String REFRESH_DB_ID = REFRESH_DB_PREFIX + ".data_id";
}
