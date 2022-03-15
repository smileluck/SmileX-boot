package top.zsmile.modules.generator.config;

import lombok.Data;

/**
 * @author: B.Smile
 * @Date: 2022/3/15 16:36
 * @Description:
 */
@Data
public class GeneratorConfig {
    /**
     * 包路径
     */
    private String packages;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 作者
     */
    private String author;
    /**
     * 邮箱
     */
    private String email;
}
