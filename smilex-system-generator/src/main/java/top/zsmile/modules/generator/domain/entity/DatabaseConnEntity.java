package top.zsmile.modules.generator.domain.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class DatabaseConnEntity implements Serializable {
    private static final long serialVersionUID = -4123125798831566630L;
    /**
     * 连接类型：mysql
     */
    @NotBlank(message = "请选择连接类型")
    private String type;

    /**
     * 连接地址
     */
    @NotBlank(message = "请输入连接地址")
    private String address;

    /**
     * 连接地址端口
     */
    @Range(min = 0, max = 65535, message = "端口需在0~65535之间")
    private Integer port;

    /**
     * 连接用户
     */
    @NotBlank(message = "请输入用户名")
    private String username;

    /**
     * 连接用户密码
     */
    @NotBlank(message = "请输入用户密码")
    private String password;

    /**
     * 连接参数
     */
//    @NotBlank(message = "请输入连接参数")
    private String params;

    /**
     * 数据库
     */
    @NotBlank(message = "请输入数据库名称")
    private String databaseName;

    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:");
        sb.append(type + "://");
        sb.append(address + ":" + port + "/");
        sb.append(databaseName);
        if (StringUtils.isEmpty(params)) {
            sb.append("?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai");
        } else {
            sb.append("?" + params);
        }
        return sb.toString();
    }
}
