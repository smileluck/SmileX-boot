package top.zsmile.auth.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import top.zsmile.api.system.common.CommonApi;
import top.zsmile.auth.token.OAuth2Token;
import top.zsmile.common.core.utils.IPUtils;
import top.zsmile.common.core.utils.JwtUtils;
import top.zsmile.core.utils.SpringContextUtils;

import java.util.*;

@Slf4j
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private CommonApi commonApi;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Map<String, Object> userInfo = (Map<String, Object>) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> sets = commonApi.queryUserPerms(Long.valueOf(userInfo.get("id").toString()));
        simpleAuthorizationInfo.setStringPermissions(sets);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        if (token == null) {
            log.info("————————身份认证失败————————，IP地址记录：" + IPUtils.getIpAddrByRequest(SpringContextUtils.getHttpServletRequest()));
            throw new AuthenticationException("身份验证失败");
        }

        Long userId = JwtUtils.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException("登录失效");
        }
        Map<String, Object> userMap = commonApi.queryUserById(userId, "username", "enableFlag", "tenantId", "password");
        if (JwtUtils.verify(token, userId, userMap.get("password").toString())) {
            throw new AuthenticationException("登录失效");
        }
        if (userMap == null) {
            throw new AuthenticationException("用户不存在");
        }
        if (!Boolean.valueOf(userMap.get("enableFlag").toString())) {
            throw new AuthenticationException("用户已被锁定，请联系管理员");
        }

        userMap.remove("password");
        userMap.remove("enableFlag");
        Map<String, Object> tenantMap = commonApi.queryTenantById(userMap.get("tenantId"), "enableFlag");
        if (!Boolean.valueOf(tenantMap.get("enableFlag").toString())) {
            throw new AuthenticationException("租户已被锁定，请联系管理员");
        }

        return new SimpleAuthenticationInfo(userMap, token, getName());
    }
}
