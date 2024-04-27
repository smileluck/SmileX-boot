package top.zsmile.tool.wechat.mp.constant;

/**
 * 微信公众号静态常量
 */
public final class WechatConstant {

    /**
     * XML 消息类型
     */
    public static final class XmlMsgType {
        public static final String TEXT = "text";
        public static final String IMAGE = "image";
        public static final String VOICE = "voice";
        public static final String VIDEO = "video";
        public static final String SHORTVIDEO = "shortvideo";
        public static final String LOCATION = "location";
        public static final String LINK = "link";
        public static final String EVENT = "event";
        public static final String MUSIC = "music";
        public static final String NEWS = "news";

        private XmlMsgType() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    /**
     * Event 事件类型
     */
    public static final class XmlEventType {
        public static final String SUBSCRIBE = "subscribe";
        public static final String UNSUBSCRIBE = "unsubscribe";
        public static final String SCAN = "SCAN";
        public static final String LOCATION = "LOCATION";
        public static final String CLICK = "CLICK";
        public static final String VIEW = "VIEW";

        private XmlEventType() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }
    }

    public static final class QrCodeStatus {
        /**
         * 继续轮询
         */
        public static final Integer LOOP = 1;

        /**
         * 已注册
         */
        public static final Integer REG = 2;

        /**
         * 未注册
         */
        public static final Integer NOT_REG = 3;
    }

    public static final class InType {
        public static final String SCAN = "SCAN";
    }

    public static enum QrCodeType {
        LOGIN("login"),
        BIND("bind");


        private String type;

        QrCodeType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
