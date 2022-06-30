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
}
