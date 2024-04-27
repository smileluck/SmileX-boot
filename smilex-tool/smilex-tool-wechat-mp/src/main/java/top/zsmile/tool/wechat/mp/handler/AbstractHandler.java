package top.zsmile.tool.wechat.mp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractHandler implements InitializingBean {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
