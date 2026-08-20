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
import top.zsmile.common.web.utils.JwtUtils;
import top.zsmile.common.web.utils.SpringContextUtils;

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
        // 先判空再使用，否则用户不存在时此处直接 NPE
        if (userMap == null) {
            throw new AuthenticationException("用户不存在");
        }
        // 验签失败才应拒绝；原条件写反（验签成功反而抛异常），
        // 之前因 JwtUtils.verify 算法不匹配恒返回 false 而"负负得正"侥幸可用
        if (!JwtUtils.verify(token, userId, String.valueOf(userMap.get("password")))) {
            throw new AuthenticationException("登录失效");
        }
        if (!isEnable(userMap.get("enableFlag"))) {
            throw new AuthenticationException("用户已被锁定，请联系管理员");
        }

        userMap.remove("password");
        userMap.remove("enableFlag");
        Map<String, Object> tenantMap = commonApi.queryTenantById(userMap.get("tenantId"), "enableFlag");
        if (tenantMap == null || !isEnable(tenantMap.get("enableFlag"))) {
            throw new AuthenticationException("租户已被锁定，请联系管理员");
        }

        return new SimpleAuthenticationInfo(userMap, token, getName());
    }

    /**
     * enable_flag 为 tinyint(1)，经 MyBatis 映射可能是 Boolean(true)、
     * 也可能是 Integer/Long(1) 或字符串("1")，统一按"启用"语义判断；
     * 原 Boolean.valueOf("1") 恒为 false，属侥幸未被触发
     */
    private boolean isEnable(Object flag) {
        if (flag == null) {
            return false;
        }
        if (flag instanceof Boolean) {
            return (Boolean) flag;
        }
        String s = flag.toString();
        return "1".equals(s) || "true".equalsIgnoreCase(s);
    }
}
