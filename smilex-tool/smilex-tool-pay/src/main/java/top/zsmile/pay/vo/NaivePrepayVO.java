package top.zsmile.pay.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Naive Prepay响应结果
 */
public class NaivePrepayVO {
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 二维码URL
     */
    private String codeUrl;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    private NaivePrepayVO() {
    }

    public static NaivePrepayVO of(Long id, String codeUrl, LocalDateTime expireIn) {
        NaivePrepayVO naivePrepayVO = new NaivePrepayVO();
        naivePrepayVO.setId(id);
        naivePrepayVO.setCodeUrl(codeUrl);
        naivePrepayVO.setExpireTime(expireIn);
        return naivePrepayVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }


    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
