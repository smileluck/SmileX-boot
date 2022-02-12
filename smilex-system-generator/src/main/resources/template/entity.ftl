package ${packages}.${moduleName}.entity;

import lombok.Data;
import java.io.Serializable;

@Data
@TableName("${tableName}")
public class ${bigHumpClass} implements Serializable {
<#list variables as var>
    /**
    * ${var.comment}
    */
    private ${var.dataType} ${var.name};
</#list>
}
