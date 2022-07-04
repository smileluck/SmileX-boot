package top.zsmile.core.api;

import java.util.Map;

/**
 * 通用权限api
 */
public interface CommonAuthApi {
    Map<String, Object> queryUserInfo();

    Long queryUserId();
}
