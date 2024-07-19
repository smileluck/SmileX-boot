package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统部门
 */
@TableName("sys_dept")
@Schema(description = "系统部门")
public class SysDeptEntity extends BaseEntity implements Serializable {
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
    * 父级部门，不存在则为0
    */
    @Schema(description = "父级部门，不存在则为0", hidden=false)
    private Long parentId;
    /**
    * 部门名称
    */
    @Schema(description = "部门名称", hidden=false)
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
