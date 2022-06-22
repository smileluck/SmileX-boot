package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class SysUserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    private Long id;
    /**
    * 租户ID
    */
    private Long tenantId;
    /**
    * 用户名
    */
    private String username;
    /**
    * 真实名称
    */
    private String realName;
    /**
    * 密码
    */
    private String password;
    /**
    * salt
    */
    private String salt;
    /**
    * 是否启用，0启用1禁用
    */
    private Integer enableFlag;
    /**
    * 备注
    */
    private String remark;
    /**
     * 是否删除，1是，0否
     */
    private Integer delFlag;

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
    * 是否启用，0启用1禁用
    */
    public Integer getEnableFlag(){
    return this.enableFlag;
    }
    /**
    * 是否启用，0启用1禁用
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
