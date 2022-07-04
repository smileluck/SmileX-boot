package top.zsmile.auth.utils;

import org.springframework.stereotype.Service;
import top.zsmile.core.api.CommonAuthApi;

import java.util.Map;

@Service("shiroAuthApi")
public class ShiroAuthApiImpl implements CommonAuthApi {
    @Override
    public Map<String, Object> queryUserInfo() {
        return (Map<String, Object>) ShiroUtils.getSubject().getPrincipal();
    }

    @Override
    public Long queryUserId() {
        Map<String, Object> map = queryUserInfo();
        Long id = Long.valueOf(map.get("id").toString());
        return id;
    }
}
