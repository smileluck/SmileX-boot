package ${packages}.${moduleName}.entity;

import lombok.Data;
import java.io.Serializable;

@Data
@TableName("${tableName}")
public class ${bigHumpClass} implements Serializable {
    private static final long serialVersionUID = 1L;

<#if primaryColumn>
    /**
    * ${primaryColumn.columnComment}
    */
    @TableId
    private ${primaryColumn.convertDataType} ${primaryColumn.humpColumnName};
</#if>
<#list variables as var>
    /**
    * ${var.columnComment}
    */
    private ${var.convertDataType} ${var.humpColumnName};
</#list>
}
