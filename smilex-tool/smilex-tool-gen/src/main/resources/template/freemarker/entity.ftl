package ${packages}.${moduleName}.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ${tableComment}
 */
@TableName("${tableName}")
@Schema(description = "${tableComment}")
public class ${bigHumpClass}Entity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

<#if primaryColumn??>
    /**
    * ${primaryColumn.columnComment}
    */
    @Schema(description = "Id，更新时需要传")
    private ${primaryColumn.convertDataType} ${primaryColumn.humpColumnName};
</#if>
<#list columnModels as var>
    /**
    * ${var.columnComment}
    */
    @Schema(description = "${var.columnComment}", hidden=${filterColumn?seq_contains(var.humpColumnName)?string("true","false")})
    private ${var.convertDataType} ${var.humpColumnName};
</#list>

<#if primaryColumn??>
    /**
    * 获取：${primaryColumn.columnComment}
    */
    public ${primaryColumn.convertDataType} get${primaryColumn.bigHumpColumnName}(){
        return this.${primaryColumn.humpColumnName};
    }
    /**
    * 设置：${primaryColumn.columnComment}
    */
    public void set${primaryColumn.bigHumpColumnName}(${primaryColumn.convertDataType} ${primaryColumn.humpColumnName}){
        this.${primaryColumn.humpColumnName} = ${primaryColumn.humpColumnName};
    }
</#if>
<#list columnModels as var>
    /**
    * 获取：${var.columnComment}
    */
    public ${var.convertDataType} get${var.bigHumpColumnName}(){
        return this.${var.humpColumnName};
    }
    /**
    * 设置： ${var.columnComment}
    */
    public void set${var.bigHumpColumnName}(${var.convertDataType} ${var.humpColumnName}){
        this.${var.humpColumnName} = ${var.humpColumnName};
    }
</#list>
}
