package ${packages}.${moduleName}.entity;

import java.io.Serializable;
<#if importTypes??>
<#list importTypes as type>
<#if type == "LocalDate">
import java.time.LocalDate;
<#elseif type == "LocalDateTime">
import java.time.LocalDateTime;
<#elseif type == "LocalTime">
import java.time.LocalTime;
<#elseif type == "BigDecimal">
import java.math.BigDecimal;
</#if>
</#list>
</#if>
import top.zsmile.common.mybatis.annotation.TableId;
import top.zsmile.common.mybatis.annotation.TableName;
<#assign hasTenant = false>
<#list columnModels as var>
<#if var.columnName == "tenant_id">
<#assign hasTenant = true>
</#if>
</#list>
<#if hasTenant>
import top.zsmile.common.mybatis.annotation.TenantId;
</#if>
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ${tableComment!''}
 */
@TableName("${tableName}")
@Schema(description = "${tableComment!''}")
public class ${bigHumpClass}Entity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

<#if primaryColumn??>
    /**
    * ${primaryColumn.columnComment!''}
    */
    @TableId
    @Schema(description = "Id，更新时需要传")
    private ${primaryColumn.convertDataType} ${primaryColumn.humpColumnName};
</#if>
<#list columnModels as var>
    /**
    * ${var.columnComment!''}
    */
    <#if var.columnName == "tenant_id">
    @TenantId
    </#if>
    @Schema(description = "${var.columnComment!''}", hidden=${filterColumn?seq_contains(var.humpColumnName)?string("true","false")})
    private ${var.convertDataType} ${var.humpColumnName};
</#list>

<#if primaryColumn??>
    /**
    * 获取：${primaryColumn.columnComment!''}
    */
    public ${primaryColumn.convertDataType} get${primaryColumn.bigHumpColumnName}(){
        return this.${primaryColumn.humpColumnName};
    }
    /**
    * 设置：${primaryColumn.columnComment!''}
    */
    public void set${primaryColumn.bigHumpColumnName}(${primaryColumn.convertDataType} ${primaryColumn.humpColumnName}){
        this.${primaryColumn.humpColumnName} = ${primaryColumn.humpColumnName};
    }
</#if>
<#list columnModels as var>
    /**
    * 获取：${var.columnComment!''}
    */
    public ${var.convertDataType} get${var.bigHumpColumnName}(){
        return this.${var.humpColumnName};
    }
    /**
    * 设置： ${var.columnComment!''}
    */
    public void set${var.bigHumpColumnName}(${var.convertDataType} ${var.humpColumnName}){
        this.${var.humpColumnName} = ${var.humpColumnName};
    }
</#list>
}
