package top.zsmile.pay.bean;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 微信支付V3通知
 */
public class WxV3Notify {
    private String id;

    /**
     * 通知创建的时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss.表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示北京时间2015年05月20日13点29分35秒。
     */
    @JsonAlias({"createTime", "create_time"})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date createTime;


    /**
     * 通知的类型，支付成功通知的类型为TRANSACTION.SUCCESS。
     */
    @JsonAlias({"eventType", "event_type"})
    private String eventType;


    /**
     * 通知的类型，支付成功通知的类型为TRANSACTION.SUCCESS。
     */
    @JsonAlias({"resourceType", "resource_type"})
    private String resource_type;

    /**
     * 通知资源数据。
     */
    private Resouce resource;
    /**
     * 回调摘要
     */
    private String summary;


    private final class Resouce {
        /**
         * 对开启结果数据进行加密的加密算法，目前只支持AEAD_AES_256_GCM。
         */
        @JsonAlias({"original_type", "originalType"})
        private String original_type;

        /**
         * Base64编码后的开启/停用结果数据密文。
         */
        private String algorithm;

        /**
         * 附加数据。
         */
        private String ciphertext;

        /**
         * 原始回调类型，为transaction。
         */
        @JsonAlias({"associated_data", "associatedData"})
        private String associated_data;

        /**
         * 加密使用的随机串。
         */
        private String nonce;

        public String getOriginal_type() {
            return original_type;
        }

        public void setOriginal_type(String original_type) {
            this.original_type = original_type;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getCiphertext() {
            return ciphertext;
        }

        public void setCiphertext(String ciphertext) {
            this.ciphertext = ciphertext;
        }

        public String getAssociated_data() {
            return associated_data;
        }

        public void setAssociated_data(String associated_data) {
            this.associated_data = associated_data;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public Resouce getResource() {
        return resource;
    }

    public void setResource(Resouce resource) {
        this.resource = resource;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
