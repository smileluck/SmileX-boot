package top.zsmile.modules.generator.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeneratorEntity implements Serializable {
    private static final long serialVersionUID = -5033545272962428672L;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 表名
     */
    private String tableName;
}
