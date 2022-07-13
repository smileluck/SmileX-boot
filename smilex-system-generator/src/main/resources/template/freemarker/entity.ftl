package ${packages}.${moduleName}.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * tableComment
 */
<#--@TableName("${tableName}")-->
@ApiModel(value="${tableComment}", description = "${tableComment}")
public class ${bigHumpClass}Entity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

<#if primaryColumn??>
    /**
    * ${primaryColumn.columnComment}
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private ${primaryColumn.convertDataType} ${primaryColumn.humpColumnName};
</#if>
<#list columnModels as var>
    /**
    * ${var.columnComment}
    */
    @ApiModelProperty(value = "${var.columnComment}", hidden=${filterColumn?seq_contains(var.humpColumnName)?string("true","false")})
    private ${var.convertDataType} ${var.humpColumnName};
</#list>

<#if primaryColumn??>
    /**
    * ${primaryColumn.columnComment}
    */
    public ${primaryColumn.convertDataType} get${primaryColumn.bigHumpColumnName}(){
        return this.${primaryColumn.humpColumnName};
    }
    /**
    * ${primaryColumn.columnComment}
    */
    public void set${primaryColumn.bigHumpColumnName}(${primaryColumn.convertDataType} ${primaryColumn.humpColumnName}){
        this.${primaryColumn.humpColumnName} = ${primaryColumn.humpColumnName};
    }
</#if>
<#list columnModels as var>
    /**
    * ${var.columnComment}
    */
    public ${var.convertDataType} get${var.bigHumpColumnName}(){
        return this.${var.humpColumnName};
    }
    /**
    * ${var.columnComment}
    */
    public void set${var.bigHumpColumnName}(${var.convertDataType} ${var.humpColumnName}){
        this.${var.humpColumnName} = ${var.humpColumnName};
    }
</#list>
}
