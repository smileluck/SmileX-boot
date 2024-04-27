package top.zsmile.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具
 */
public class IPUtils {

    private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);
    private static final String LOCAL_IP = "127.0.0.1";

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

    /**
     * 获取服务器IP地址
     *
     * @return 服务器IP地址
     */
    public static String getServerIp() {
        String ip;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            ip = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ip = LOCAL_IP;
            logger.error("IPUtil getServerIp", e);
        }
        return ip;
    }
}
