package top.zsmile.api.common;

import java.util.List;
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
}
