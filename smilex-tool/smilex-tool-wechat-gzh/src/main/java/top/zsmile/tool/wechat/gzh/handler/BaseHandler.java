package top.zsmile.tool.wechat.gzh.handler;

import top.zsmile.common.core.exception.SXException;
import top.zsmile.tool.wechat.gzh.properties.WxAuthCacheInfo;

public abstract class BaseHandler {
    /**
     * 类型
     */
    protected Integer type;

    /**
     * 执行方法
     *
     * @param cacheInfo
     */
    public abstract void exec(WxAuthCacheInfo cacheInfo) throws SXException;

    /**
     * 响应结果
     */
    public abstract Object resp(WxAuthCacheInfo cacheInfo);

    public String returnErr(WxAuthCacheInfo cacheInfo, String message) throws SXException {
        cacheInfo.setStatus(4);
        cacheInfo.setMsg(message);
        throw new SXException(message);
    }
}
