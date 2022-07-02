package top.zsmile.core.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    List<Object> queryUserPerms(Long userId);
}
