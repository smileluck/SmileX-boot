package top.zsmile.system.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统部门
 */
@TableName("sys_dept")
@ApiModel(value="系统部门", description = "系统部门")
public class SysDeptEntity extends BaseEntity implements Serializable {
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
    * 父级部门，不存在则为0
    */
    @ApiModelProperty(value = "父级部门，不存在则为0", hidden=false)
    private Long parentId;
    /**
    * 部门名称
    */
    @ApiModelProperty(value = "部门名称", hidden=false)
    private String deptName;

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
    * 父级部门，不存在则为0
    */
    public Long getParentId(){
        return this.parentId;
    }
    /**
    * 父级部门，不存在则为0
    */
    public void setParentId(Long parentId){
        this.parentId = parentId;
    }
    /**
    * 部门名称
    */
    public String getDeptName(){
        return this.deptName;
    }
    /**
    * 部门名称
    */
    public void setDeptName(String deptName){
        this.deptName = deptName;
    }
}
