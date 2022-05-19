package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class SysTenantEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    private Long id;
    /**
    * 租户名称
    */
    private String tenantName;

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
}
