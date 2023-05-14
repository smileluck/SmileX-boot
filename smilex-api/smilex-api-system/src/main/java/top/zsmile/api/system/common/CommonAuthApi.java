package top.zsmile.api.system.common;

import java.util.Map;

/**
 * 通用权限api
 */
public interface CommonAuthApi {
    /**
     * 查询当前用户信息
     * @return
     */
    Map<String, Object> queryUserInfo();

    /**
     * 查询当前用户ID
     * @return
     */
    Long queryUserId();

    /**
     * 查询当前用户租户ID
     */
    Long queryTenantId();
}
