package top.zsmile.common.core.utils;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
    /**
     * 根据servlet请求获取ip地址
     *
     * @param httpServletRequest
     * @return
     */
    public static String getIpAddrByRequest(HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }
}
