package top.zsmile.system.boot.utils;

import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.core.utils.SpringContextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库查询辅助工具
 */
public class SqlUtils {

    private static CommonAuthApi commonAuthApi = null;

    static {
        commonAuthApi = SpringContextUtils.getBean(CommonAuthApi.class);
    }

    public static Map<String, Object> tenantMap() {
        return tenantMap(1);
    }

    public static Map<String, Object> tenantMap(int initCapacity) {
        Map<String, Object> map = new HashMap<>();
        map.put("tenantId", commonAuthApi.queryTenantId());
        return map;
    }
}
