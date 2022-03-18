package top.zsmile.modules.generator.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class DatabaseConnEntity implements Serializable {
    private static final long serialVersionUID = -4123125798831566630L;
    /**
     * 连接类型：mysql
     */
    @NotBlank(message = "连接类型不能为空")
    private String type;

    /**
     * 连接地址
     */
    @NotBlank(message = "地址不能为空")
    private String address;

    /**
     * 连接地址端口
     */
    private Integer port;

    /**
     * 连接用户
     */
    private String username;

    /**
     * 连接用户密码
     */
    private String password;

    /**
     * 连接参数
     */
    private String params;

    /**
     * 数据库
     */
    private String databaseName;

    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:");
        sb.append(type + "://");
        sb.append(address + ":" + port + "/");
        sb.append(databaseName);
        sb.append("?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai");
        return sb.toString();
    }
}
