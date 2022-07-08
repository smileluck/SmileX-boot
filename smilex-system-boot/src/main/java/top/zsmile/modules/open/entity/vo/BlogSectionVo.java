package top.zsmile.modules.open.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogSectionVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 栏目名称
     */
    private String sectionName;
    /**
     * 访问类型，1无限制，2统一密码访问
     */
    private Integer visitType;
}
