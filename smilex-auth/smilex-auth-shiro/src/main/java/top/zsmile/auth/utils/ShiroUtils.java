package top.zsmile.auth.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


/**
 * Shiro工具类
 */
public class ShiroUtils {
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Object getSessionAttr(String key) {
        return getSession().getAttribute(key);
    }

    public static void setSessionAttr(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static String sha256Hash(String password, String salt) {
        return new Sha256Hash(password, salt).toHex();
    }
}
