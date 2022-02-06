package top.zsmile.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

public class SysToken implements AuthenticationToken {
    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
