package top.zsmile.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import top.zsmile.api.system.common.CommonAuthApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ShiroAuthApiImpl implements CommonAuthApi, InitializingBean {

    private static Map userInfo = null;

    @Override
    public Map<String, Object> queryUserInfo() {
        try {
            Map<String, Object> principal = (Map<String, Object>) ShiroUtils.getSubject().getPrincipal();
            if (principal == null) {
                log.info("用户信息为空");
                return userInfo;
            } else {
                return principal;
            }
        } catch (Exception e) {
            log.info("非用户执行SQL");
            return userInfo;
        }
    }

    @Override
    public Long queryUserId() {
        Map<String, Object> map = queryUserInfo();
        Long id = Long.valueOf(map.get("id").toString());
        return id;
    }

    @Override
    public Long queryTenantId() {
        Map<String, Object> map = queryUserInfo();
        Long id = Long.valueOf(map.get("tenantId").toString());
        return id;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map mutableMap = new HashMap<>();
        mutableMap.put("username", "AUTO");
        userInfo = Collections.unmodifiableMap(mutableMap);
    }
}
