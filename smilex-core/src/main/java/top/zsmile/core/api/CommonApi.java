package top.zsmile.core.api;

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
}
