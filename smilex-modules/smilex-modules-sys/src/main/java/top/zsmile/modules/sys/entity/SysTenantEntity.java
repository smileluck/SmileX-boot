package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 多租户管理
 */
@TableName("sys_tenant")
@Schema(description = "多租户管理")
public class SysTenantEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 租户名称
    */
    @Schema(description = "租户名称", hidden=false)
    private String tenantName;
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

    /**
     * 是否启用，0禁用1启用
     */
    public Integer getEnableFlag() {
        return enableFlag;
    }

    /**
     * 是否启用，0禁用1启用
     */
    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }
}
