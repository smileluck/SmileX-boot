package top.zsmile.api.system.common;

import java.util.Map;
import java.util.Set;

/**
 * 通用API
 */
public interface CommonApi {

    /**
     * 1查询用户信息
     *
     * @param userId 用户id
     * @return
     */
    Map<String, Object> queryUserById(Long userId, String... columns);

    /**
     * 2查询用户权限信息
     */
    Set<String> queryUserPerms(Long userId);

    /**
     * 查询租户信息
     * @param tenantId
     * @param column
     * @return
     */
    Map<String, Object> queryTenantById(Object tenantId, String... column);
}
