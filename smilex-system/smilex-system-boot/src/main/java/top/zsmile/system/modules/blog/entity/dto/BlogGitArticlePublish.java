package top.zsmile.system.modules.blog.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.zsmile.common.core.validator.group.Add;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "Git文章发表或更新")
public class BlogGitArticlePublish {


    /**
     * ID
     */
    @NotNull(message = "请选中GIT文章")
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
     * 栏目ID
     */
    @NotNull(message = "请选择栏目", groups = Add.class)
    @ApiModelProperty(value = "栏目ID")
    private Long sectionId;
    /**
     * 标签id，以,分割
     */
    @NotBlank(message = "请选择标签", groups = Add.class)
    @ApiModelProperty(value = "标签id，以,分割")
    private String tagIds;
    /**
     * 文章封面
     */
    @ApiModelProperty(value = "文章封面")
    private String poster;
    /**
     * 文章标题
     */
    @NotBlank(message = "请输入文章标题", groups = Add.class)
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;
    /**
     * 文章简介
     */
    @NotBlank(message = "请输入文章简介", groups = Add.class)
    @ApiModelProperty(value = "文章简介")
    private String articleDigest;
    /**
     * 文章内容
     */
    @NotBlank(message = "请输入文章内容", groups = Add.class)
    @ApiModelProperty(value = "文章内容")
    private String articleContent;
    /**
     * 语法类型，1markdown，2html
     */
//    @NotNull(message = "请选择语法类型", groups = Add.class)
//    @ApiModelProperty(value = "语法类型，1markdown，2html")
//    private Integer grammarType;
    /**
     * 访问类型,1通用类型，2统一密码，3独立密码
     */
    @NotNull(message = "请选择访问类型", groups = Add.class)
    @ApiModelProperty(value = "访问类型,1通用类型，2统一密码，3独立密码")
    private Integer visitType;
    /**
     * 独立密码
     */
    @ApiModelProperty(value = "独立密码")
    private String password;
}
