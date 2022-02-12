package top.zsmile.modules.generator.model;

import lombok.Data;

import java.util.List;

@Data
public class RootModel {
    /**
     * 包路径
     */
    private String packages;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 大驼峰类名
     */
    private String bigHumpClass;
    /**
     * 小驼峰类名
     */
    private String smallHumClass;
}
