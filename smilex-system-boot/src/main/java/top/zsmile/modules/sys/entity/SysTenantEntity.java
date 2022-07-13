package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 多租户管理
 */
@ApiModel(value="多租户管理", description = "多租户管理")
public class SysTenantEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 租户名称
    */
    @ApiModelProperty(value = "租户名称", hidden=false)
    private String tenantName;
    /**
    * 密码
    */
    @ApiModelProperty(value = "密码", hidden=true)
    private String password;
    /**
    * salt
    */
    @ApiModelProperty(value = "salt", hidden=true)
    private String salt;

    /**
    * ID
    */
    public Long getId(){
        return this.id;
    }
    /**
    * ID
    */
    public void setId(Long id){
        this.id = id;
    }
    /**
    * 租户名称
    */
    public String getTenantName(){
        return this.tenantName;
    }
    /**
    * 租户名称
    */
    public void setTenantName(String tenantName){
        this.tenantName = tenantName;
    }
    /**
    * 密码
    */
    public String getPassword(){
        return this.password;
    }
    /**
    * 密码
    */
    public void setPassword(String password){
        this.password = password;
    }
    /**
    * salt
    */
    public String getSalt(){
        return this.salt;
    }
    /**
    * salt
    */
    public void setSalt(String salt){
        this.salt = salt;
    }
}
