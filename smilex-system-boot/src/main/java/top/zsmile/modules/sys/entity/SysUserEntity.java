package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统用户管理
 */
@ApiModel(value="系统用户管理", description = "系统用户管理")
public class SysUserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 租户ID
    */
    @ApiModelProperty(value = "租户ID", hidden=false)
    private Long tenantId;
    /**
    * 用户名
    */
    @ApiModelProperty(value = "用户名", hidden=false)
    private String username;
    /**
    * 真实名称
    */
    @ApiModelProperty(value = "真实名称", hidden=false)
    private String realName;
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
    * 是否启用，0禁用1启用
    */
    @ApiModelProperty(value = "是否启用，0禁用1启用", hidden=false)
    private Integer enableFlag;
    /**
    * 备注
    */
    @ApiModelProperty(value = "备注", hidden=false)
    private String remark;

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
    * 租户ID
    */
    public Long getTenantId(){
        return this.tenantId;
    }
    /**
    * 租户ID
    */
    public void setTenantId(Long tenantId){
        this.tenantId = tenantId;
    }
    /**
    * 用户名
    */
    public String getUsername(){
        return this.username;
    }
    /**
    * 用户名
    */
    public void setUsername(String username){
        this.username = username;
    }
    /**
    * 真实名称
    */
    public String getRealName(){
        return this.realName;
    }
    /**
    * 真实名称
    */
    public void setRealName(String realName){
        this.realName = realName;
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
    /**
    * 是否启用，0禁用1启用
    */
    public Integer getEnableFlag(){
        return this.enableFlag;
    }
    /**
    * 是否启用，0禁用1启用
    */
    public void setEnableFlag(Integer enableFlag){
        this.enableFlag = enableFlag;
    }
    /**
    * 备注
    */
    public String getRemark(){
        return this.remark;
    }
    /**
    * 备注
    */
    public void setRemark(String remark){
        this.remark = remark;
    }
}
