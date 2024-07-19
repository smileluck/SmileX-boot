package top.zsmile.system.boot.modules.open.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Schema(description = "博客 - 栏目")
public class BlogSectionVo {
    /**
     * ID
     */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
     * 父ID,最上级为0
     */
    @Schema(description = "父ID,最上级为0", hidden = false)
    private Long parentId;
    /**
     * 栏目名称
     */
    @Schema(description = "栏目名称", hidden = false)
    private String sectionName;
    /**
     * 访问类型，1无限制，2统一密码访问
     */
    @Schema(description = "访问类型，1无限制，2统一密码访问", hidden = false)
    private Integer visitType;
}
