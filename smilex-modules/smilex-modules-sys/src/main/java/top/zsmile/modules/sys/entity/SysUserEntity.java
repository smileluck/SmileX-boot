package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统用户管理
 */
@TableName("sys_user")
@Schema(description = "系统用户管理")
public class SysUserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 租户ID
    */
    @Schema(description = "租户ID", hidden=false)
    private Long tenantId;
    /**
    * 用户名
    */
    @Schema(description = "用户名", hidden=false)
    private String username;
    /**
    * 真实名称
    */
    @Schema(description = "真实名称", hidden=false)
    private String realName;
    /**
    * 密码
    */
    @Schema(description = "密码", hidden=true)
    private String password;
    /**
    * salt
    */
    @Schema(description = "salt", hidden=true)
    private String salt;
    /**
    * 是否启用，0禁用1启用
    */
    @Schema(description = "是否启用，0禁用1启用", hidden=false)
    private Integer enableFlag;
    /**
    * 备注
    */
    @Schema(description = "备注", hidden=false)
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
